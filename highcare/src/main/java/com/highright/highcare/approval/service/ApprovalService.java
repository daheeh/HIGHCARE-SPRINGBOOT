package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.*;

import com.highright.highcare.approval.repository.ApprovalRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Object insertApvFamilyEvent(ApvFormDTO apvFormDTO) {
        log.info("[ApprovalService] insertApvFamilyEvent --------------- start ");

        try {
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            if (apvFormDTO.getApvFamilyEvents() != null) {
                List<ApvFamilyEvent> apvFamilyEvents = new ArrayList<>();
                for (ApvFamilyEventDTO familyEventDTO : apvFormDTO.getApvFamilyEvents()) {
                    ApvFamilyEvent apvFamilyEvent = modelMapper.map(familyEventDTO, ApvFamilyEvent.class);
                    apvFamilyEvent.setApvForm(apvForm);
                    apvFamilyEvents.add(apvFamilyEvent);
                }
                apvForm.setApvFamilyEvents(apvFamilyEvents);
            }

            approvalRepository.save(apvForm);

            log.info("[ApprovalService] insertApvFamilyEvent --------------- end ");
            return "기안 상신 성공";
        } catch (Exception e){
            log.error("[ApprovalService] Error insertApvFamilyEvent : " + e.getMessage());
            return "기안 상신 실패";
        }
    }



    @Transactional
    public Object insertApvVacation(ApvFormDTO apvFormDTO) {

        log.info("[ApprovalService] insertApvVacation --------------- start ");

        try {
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            if (apvFormDTO.getApvVacations() != null) {
                List<ApvVacation> apvVacations = new ArrayList<>();
                for (ApvVacationDTO vacationDTO : apvFormDTO.getApvVacations()) {
                    ApvVacation apvVacation = modelMapper.map(vacationDTO, ApvVacation.class);
                    apvVacation.setApvForm(apvForm);
                    apvVacations.add(apvVacation);
                }
                apvForm.setApvVacations(apvVacations);
            }

            approvalRepository.save(apvForm);

            log.info("[ApprovalService] insertApvVacation --------------- end ");
            return "기안 상신 성공";
        } catch (Exception e){
            log.error("[ApprovalService] Error insertApvVacation : " + e.getMessage());
            return "기안 상신 실패";
        }
    }

    @Transactional
    public Object insertApvIssuance(ApvFormDTO apvFormDTO) {

        log.info("[ApprovalService] insertApvIssuance --------------- start ");

        try {
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            if (apvFormDTO.getApvIssuances() != null) {
                List<ApvIssuance> apvIssuances = new ArrayList<>();
                for (ApvIssuanceDTO apvIssuanceDTO : apvFormDTO.getApvIssuances()) {
                    ApvIssuance apvIssuance = modelMapper.map(apvIssuanceDTO, ApvIssuance.class);
                    apvIssuance.setApvForm(apvForm);
                    apvIssuances.add(apvIssuance);
                }
                apvForm.setApvIssuances(apvIssuances);
            }

            approvalRepository.save(apvForm);

            log.info("[ApprovalService] insertApvIssuance --------------- end ");
            return "기안 상신 성공";
        } catch (Exception e){
            log.error("[ApprovalService] Error insertApvIssuance : " + e.getMessage());
            return "기안 상신 실패";
        }
    }


    public List<ApvFormDTO> selectWriteApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- start ");

        List<ApvForm> writeApvList = approvalRepository.findByEmpNoAndApvStatus(empNo, apvStatus);

        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
        return writeApvList.stream().map(apvForm -> modelMapper.map(apvForm, ApvFormDTO.class)).collect(Collectors.toList());
    }



}
