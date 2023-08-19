package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.repository.ApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

        try {
            ApvForm insertApvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            approvalRepository.save(insertApvForm);
            log.info("[ApprovalService] insertApvForm --------------- end ");
            return "기안 상신 성공";
        } catch (Exception e){
            log.error("[ApprovalService] Error insertApvForm : " + e.getMessage());
            return "기안 상신 실패";
        }
    }

    public List<ApvFormDTO> selectWriteApvList(int empNo) {
        log.info("[ApprovalService] selectWriteApvList --------------- start ");

        List<ApvForm> writeApvList = approvalRepository.findByEmpNo(empNo);

        log.info("[ApprovalService] selectWriteApvList --------------- end ");
        return writeApvList.stream().map(apvForm -> modelMapper.map(apvForm, ApvFormDTO.class)).collect(Collectors.toList());
    }
}
