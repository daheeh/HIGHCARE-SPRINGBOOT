package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.entity.ADMAccount;
import com.highright.highcare.admin.entity.ADMAuthAccount;
import com.highright.highcare.admin.entity.ADMAuthAccountId;
import com.highright.highcare.admin.repository.ADMAccountRepository;
import com.highright.highcare.admin.repository.ADMAuthAccountRepository;
import com.highright.highcare.admin.repository.ADMEmployeeRepository;
import com.highright.highcare.auth.dto.AccountDTO;
import com.highright.highcare.auth.entity.ADMEmployee;
import com.highright.highcare.common.AdminCustomBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ModelMapper modelMapper;
    private final ADMEmployeeRepository admEmployeeRepository;
    private final ADMAccountRepository admAccountRepository;
    private final ADMAuthAccountRepository admAuthAccountRepository;

    private final AdminCustomBean customBean;

    @Override
    public Object selectMember(int empNo) {

        // 리빌딩 --- 전체 사원 조회 (if 이미 회원인 사원은 조회불가)
        ADMEmployee findMember = admEmployeeRepository.findById(empNo).get();

        log.info("[AdminServiceImpl] selectMember : findMember ==== {}",findMember);

        return  RequestMemberDTO.builder()
                .empNo(findMember.getEmpNo())
                .Name(findMember.getName())
                .jobName(findMember.getJobCode().getJobName())
                .deptName(findMember.getDeptCode().getDeptName())
                .phone(findMember.getPhone())
                .email(findMember.getEmail())
                .build();
    }

    @Transactional
    @Override
    public Object insertMember(RequestMemberDTO requestMemberDTO) {

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
            admAccountRepository.save(modelMapper.map(account, ADMAccount.class));
            admAuthAccountRepository.save(ADMAuthAccount.builder().id(authAccountId).build());

            return "회원 임시등록 성공";
        } catch(Exception e) {

            return "회원 임시등록 실패";
        }



    }
}
