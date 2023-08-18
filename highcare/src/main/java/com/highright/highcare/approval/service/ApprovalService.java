package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.repository.ApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Object insertApvForm(ApvFormDTO apvFormDTO) {
        return null;
    }
}
