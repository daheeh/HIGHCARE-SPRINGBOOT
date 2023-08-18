package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.repository.ApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ApprovalService(ApprovalRepository approvalRepository, ModelMapper modelMapper) {
        this.approvalRepository = approvalRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public Object insertApvForm(ApvFormDTO apvFormDTO) {
        log.info("[ApprovalService] insertApvForm --------------- start ");

        int result = 0;

        ApvForm insertApvForm = modelMapper.map(apvFormDTO, ApvForm.class);
        approvalRepository.save(insertApvForm);
        result = 1;


        log.info("[ApprovalService] insertApvForm --------------- end ");
        return (result > 0)? "기안 상신 성공" : "기안 상신 실패";
    }
}
