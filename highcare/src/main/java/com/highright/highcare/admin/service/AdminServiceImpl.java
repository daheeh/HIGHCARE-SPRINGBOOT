package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.ADMAccountDTO;
import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.dto.UpdateAccountDTO;
import com.highright.highcare.admin.entity.*;
import com.highright.highcare.admin.repository.ADMAccountRepository;
import com.highright.highcare.admin.repository.ADMAuthAccountRepository;
import com.highright.highcare.admin.repository.ADMEmployeeRepository;
import com.highright.highcare.admin.repository.AccessManagerRepository;
import com.highright.highcare.auth.dto.AccountDTO;
import com.highright.highcare.auth.entity.AUTHAccount;
//import com.highright.highcare.auth.entity.AUTHEmployee;
import com.highright.highcare.common.AdminCustomBean;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.common.repository.DeptRespository;
import com.highright.highcare.common.repository.JobRepository;
import com.highright.highcare.mypage.entity.Department;
import com.highright.highcare.mypage.entity.Job;
import com.highright.highcare.pm.repository.PmDepartmentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ModelMapper modelMapper;
    private final ADMEmployeeRepository admEmployeeRepository;
    private final ADMAccountRepository admAccountRepository;
    private final ADMAuthAccountRepository admAuthAccountRepository;

    private final JobRepository jobRepository;
    private final DeptRespository deptRespository;

    private final AccessManagerRepository accessManagerRepository;
    private final AdminCustomBean customBean;

    private final JavaMailSender javaMailSender;




    @Override
    public Object selectMember(int empNo) {

        if(admAccountRepository.findByEmpNo(empNo) != null ){
            return "fail 해당 사원은 이미 회원입니다.";
        } else {
            ADMEmployee findMember = admEmployeeRepository.findById(empNo).orElse(null);
            if(findMember == null) {
                return "fail 존재하지 않는 사원번호입니다. 사원번호를 다시한번 확인하세요.";
            } else {
            return  RequestMemberDTO.builder()
                    .empNo(findMember.getEmpNo())
                    .Name(findMember.getName())
                    .jobName(findMember.getJob().getJobName())
                    .deptName(findMember.getDept().getDeptName())
                    .phone(findMember.getPhone())
                    .email(findMember.getEmail())
                    .build();
            }
        }
    }

    @Transactional
    @Override
    public Object insertAccount(RequestMemberDTO requestMemberDTO) {

        // 일반계정 등록 -> 계정별권한(임시회원) 까지만 진행됨(이후 절차는 회원등록 승인 후)
        log.info("[AdminServiceImpl] insertMember ===== start " );

        AccountDTO account = AccountDTO.builder()
                .memberId(customBean.idAccountGenerator(requestMemberDTO))
                .empNo(requestMemberDTO.getEmpNo())
                .password(customBean.randomPassword())
                .isTempPwd("Y")
                .pwdExpiredDate(Date.valueOf(LocalDate.now().plusMonths(3))) // 3개월후 비밀번호 만료
                .build();

        log.info("[AdminServiceImpl] insertMember == account ==={}", account);
        ADMAuthAccountId authAccountId = ADMAuthAccountId.builder()
                                        .code("ROLE_PRE_USER")
                                        .id(account.getMemberId())
                                        .build();
        log.info("[AdminServiceImpl] insertMember == authAccountId ==={}", authAccountId);

        try {
            // 계정등록
            ADMAccount joinInfo = admAccountRepository.save(modelMapper.map(account, ADMAccount.class));
            // 권한 등록(pre_user)
            admAuthAccountRepository.save(ADMAuthAccount.builder().id(authAccountId).build());
            // 접근관리 테이블 등록(isLock)
            accessManagerRepository.save(AccessManager.builder().id(account.getMemberId())
                    .registDate(new Timestamp(System.currentTimeMillis()))
                            .isLock("Y")
                    .build());

            // 메일보내기,
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(requestMemberDTO.getEmail());
            message.setSubject("[하이케어] 임시회원 아이디, 비밀번호 발송");
            message.setText("회원 아이디 : " + joinInfo.getMemberId() +
            "회원 비밀번호 : " + joinInfo.getPassword());

            javaMailSender.send(message);

            log.info("[AdminServiceImpl] insertMember == message ==={}", message);

            return "회원 임시등록 성공";
        } catch(Exception e) {
            return "회원 임시등록 실패";
        }
    }

    @Override
    public Object selectAccountList() {

        List<ADMAccount> authAccountList = admAccountRepository.findAllByOrderByEmpNoAsc();

        log.info("[AdminServiceImpl] selectMemberList authAccountList ==={}", authAccountList);
        List<ADMAccountDTO> accountDTOList = authAccountList.stream().map(account -> modelMapper.map(account, ADMAccountDTO.class)).collect(Collectors.toList());
        log.info("[AdminServiceImpl] selectMemberList accountDTOList ==={}", accountDTOList);


        return accountDTOList;
    }

    @Transactional
    @Override
    public Object updateAccount(String id, UpdateAccountDTO updateAccountDTO) {

        List<ADMAuthAccount> authAccountList = admAuthAccountRepository.findById_Id(id); // 중요!!!
        log.info("[AdminServiceImpl] updateAccount authAccountList ==={}", authAccountList);

        if (! authAccountList.isEmpty() && updateAccountDTO.getStatus() != null) {

            AccessManager accessManager = accessManagerRepository.findById(id).orElse(null); // ID에 해당하는 AccessManager 조회
            log.info("[AdminServiceImpl] updateAccount accessManager ==={}", accessManager);

            if(accessManager != null){
                // authAccountList 기존 권한 삭제 처리
                admAuthAccountRepository.deleteAll(authAccountList);

                String status = updateAccountDTO.getStatus();
                log.info("[AdminServiceImpl] updateAccount status ==={}", status);
                switch (status) {
                    case "user":
                        log.info("[AdminServiceImpl] updateAccount user ======================");

                        // 일반 회원 권한 넣기
                        admAuthAccountRepository.save(ADMAuthAccount.builder().id(ADMAuthAccountId.builder().id(id).code("ROLE_USER").build()).build());

                        accessManager.setIsLock("N");
                        accessManager.setIsInActive("N");
                        accessManager.setIsExpired("N");
                        accessManager.setIsWithDraw("N");
                        break;
                    case "isLock" :
                        log.info("[AdminServiceImpl] updateAccount isLock ======================");

                        admAuthAccountRepository.save(ADMAuthAccount.builder().id(ADMAuthAccountId.builder().id(id).code("ROLE_PRE_USER").build()).build());
                        accessManager.setIsLock("Y");
                        accessManager.setIsInActive("N");
                        accessManager.setIsExpired("N");
                        accessManager.setIsWithDraw("N");
                        break;
                    case "isInActive" :
                        log.info("[AdminServiceImpl] updateAccount isInActive ======================");

                        admAuthAccountRepository.save(ADMAuthAccount.builder().id(ADMAuthAccountId.builder().id(id).code("ROLE_PRE_USER").build()).build());
                        accessManager.setIsLock("N");
                        accessManager.setIsInActive("Y");
                        accessManager.setIsExpired("N");
                        accessManager.setIsWithDraw("N");
                        break;
                    case "isExpired" :
                        log.info("[AdminServiceImpl] updateAccount isExpired ======================");

                        admAuthAccountRepository.save(ADMAuthAccount.builder().id(ADMAuthAccountId.builder().id(id).code("ROLE_PRE_USER").build()).build());
                        accessManager.setIsLock("N");
                        accessManager.setIsInActive("N");
                        accessManager.setIsExpired("Y");
                        accessManager.setIsWithDraw("N");
                        break;
                }
            }
        }

        ADMEmployee employee = admEmployeeRepository.findByEmpNo(Integer.valueOf(updateAccountDTO.getEmpNo()));
        log.info("[AdminServiceImpl] updateAccount employee ======================{} ", employee);
        log.info("[AdminServiceImpl] updateAccount updateAccountDTO ======================{} ", updateAccountDTO);

        if(!updateAccountDTO.getName().isEmpty()){
            employee.setName(updateAccountDTO.getName());
            log.info("[AdminServiceImpl] updateAccount NAME CHANGE");

        } if(!updateAccountDTO.getJobName().isEmpty()){
            Job job = jobRepository.findByName(updateAccountDTO.getJobName());
            employee.setJobCode(job.getCode());
            log.info("[AdminServiceImpl] updateAccount JOB CHANGE ==={} ", job);
        } if(!updateAccountDTO.getDeptName().isEmpty()){
            Department dept = deptRespository.findByName(updateAccountDTO.getDeptName());
            employee.setDeptCode(dept.getCode());
            log.info("[AdminServiceImpl] updateAccount DEPT CHANGE==={} ", dept);
        } if(!updateAccountDTO.getPhone().isEmpty()){
            employee.setPhone((updateAccountDTO.getPhone()));
            log.info("[AdminServiceImpl] updateAccount PHONE CHANGE");
        } if(!updateAccountDTO.getEmail().isEmpty()){
            employee.setEmail(updateAccountDTO.getEmail());
            log.info("[AdminServiceImpl] updateAccount EMAIL CHANGE==={}");
        }

        return "사원정보 수정 성공";
    }

    @Override
    public Object deleteAccount(String id) {


        return null;
    }

}
