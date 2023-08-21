package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.ApvExpFormDTO;
import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.entity.ApvExpForm;

import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.repository.ApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Console;
import java.util.ArrayList;
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

    @Transactional
    public Object insertApvExpense(ApvFormDTO apvFormDTO) {
        log.info("[ApprovalService] insertApvExpense --------------- start ");

        try {
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            if (apvFormDTO.getApvExpForms() != null) {
                List<ApvExpForm> apvExpForms = new ArrayList<>();
                for (ApvExpFormDTO expFormDTO : apvFormDTO.getApvExpForms()) {
                    ApvExpForm apvExpForm = modelMapper.map(expFormDTO, ApvExpForm.class);
                    apvExpForm.setApvForm(apvForm);
                    apvExpForms.add(apvExpForm);
                }
                apvForm.setApvExpForms(apvExpForms);
            }

            approvalRepository.save(apvForm);

            log.info("[ApprovalService] insertApvExpense --------------- end ");
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


    public List<ApvFormDTO> selectWriteApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectWriteCategoryApvList --------------- start ");

        List<ApvForm> writeApvList = approvalRepository.findByEmpNoAndApvStatus(empNo, apvStatus);

        log.info("[ApprovalService] selectWriteCategoryApvList --------------- end ");
        return writeApvList.stream().map(apvForm -> modelMapper.map(apvForm, ApvFormDTO.class)).collect(Collectors.toList());
    }
}
