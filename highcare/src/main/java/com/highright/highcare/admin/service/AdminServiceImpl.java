package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.repository.AdminRepository;
import com.highright.highcare.auth.entity.ADMEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;

    @Override
    public Object selectMember(int empNo) {

        ADMEmployee findMember = adminRepository.findById(empNo).get();

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
}
