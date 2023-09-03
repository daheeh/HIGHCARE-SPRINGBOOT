package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.*;
import com.highright.highcare.approval.repository.*;
import com.highright.highcare.common.Criteria;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ApprovalExpService {

    private final ModelMapper modelMapper;
    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvFormRepository apvFormRepository;
    private final ApvLineRepository apvLineRepository;
    private final ApvExpFormRepository apvExpFormRepository;
    private final ApvFamilyEventRepository apvFamilyEventRepository;
    private final ApvCorpCardRepository apvCorpCardRepository;

    @Autowired
    public ApprovalExpService(ModelMapper modelMapper,
                              ApvFormMainRepository apvFormMainRepository,
                              ApvFormRepository apvFormRepository,
                              ApvLineRepository apvLineRepository,
                              ApvExpFormRepository apvExpFormRepository,
                              ApvFamilyEventRepository apvFamilyEventRepository,
                              ApvCorpCardRepository apvCorpCardRepository
    ) {
        this.modelMapper = modelMapper;
        this.apvFormMainRepository = apvFormMainRepository;
        this.apvFormRepository = apvFormRepository;
        this.apvLineRepository = apvLineRepository;
        this.apvExpFormRepository = apvExpFormRepository;
        this.apvFamilyEventRepository = apvFamilyEventRepository;
        this.apvCorpCardRepository = apvCorpCardRepository;
    }

    /* 전자결재 - 지출 : exp1 지출결의서, exp4 출장경비정산서 */
    @Transactional
    public Boolean insertApvExpense(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvExpense --------------- start ");
        log.info("[ApprovalHrmService] Exp1 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvExpFormDTO> apvExpFormDTO = apvFormDTO.getApvExpForms();

            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);

            ApvFormMain savedApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(savedApvForm.getApvNo());

            apvExpFormDTO.forEach(apvExpForm -> apvExpForm.setApvNo(savedApvForm.getApvNo()));

            List<ApvExpForm> apvExpFormList = apvExpFormDTO.stream()
                    .map(item ->{
                        ApvExpForm apvExpForm = modelMapper.map(item, ApvExpForm.class);
                        apvExpForm.setApvNo(savedApvForm.getApvNo());
                        return apvExpForm;
                    })
                    .collect(Collectors.toList());

            List<ApvExpForm> savedApvExpFormList = apvExpFormRepository.saveAll(apvExpFormList);

            IntStream.range(0, apvForm.getApvVacations().size())
                    .forEach(i -> {
                        ApvExpForm apvExpFormToUpdate = apvForm.getApvExpForms().get(i);
                        apvExpFormToUpdate.setApvNo(savedApvExpFormList.get(i).getApvNo());
                        apvForm.getApvExpForms().set(i, apvExpFormToUpdate);
                    });

            apvLineDTO.forEach(apvLine -> apvLine.setApvNo(savedApvForm.getApvNo()));

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태 확인 후 결재 상태 변경
            int approved = apvLineRepository.apvNoAllApproved(apvFormMain.getApvNo());
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvFormMain.getApvNo());
            }
            log.info("[ApprovalService] Exp1 insertApvExpense --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Exp1 insertApvExpense : " + e.getMessage());
            return false;
        }
    }


    /* 전자결재 - 지출 : exp6 경조금 신청서 */
    public Boolean insertApvFamilyEvent(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvFamilyEvent --------------- start ");
        log.info("[ApprovalHrmService] Exp6 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvFamilyEventDTO> apvFamilyEventDTO = apvFormDTO.getApvFamilyEvents();

            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);

            ApvFormMain savedApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(savedApvForm.getApvNo());

            apvFamilyEventDTO.forEach(apvFamilyEvent -> apvFamilyEvent.setApvNo(savedApvForm.getApvNo()));

            List<ApvFamilyEvent> apvFamilyEventList = apvFamilyEventDTO.stream()
                    .map(item ->{
                        ApvFamilyEvent apvFamilyEvent = modelMapper.map(item, ApvFamilyEvent.class);
                        apvFamilyEvent.setApvNo(savedApvForm.getApvNo());
                        return apvFamilyEvent;
                    })
                    .collect(Collectors.toList());

            List<ApvFamilyEvent> savedApvFamilyEventList = apvFamilyEventRepository.saveAll(apvFamilyEventList);

            IntStream.range(0, apvForm.getApvVacations().size())
                    .forEach(i -> {
                        ApvFamilyEvent apvFamilyEventToUpdate = apvForm.getApvFamilyEvents().get(i);
                        apvFamilyEventToUpdate.setApvNo(savedApvFamilyEventList.get(i).getApvNo());
                        apvForm.getApvFamilyEvents().set(i, apvFamilyEventToUpdate);
                    });

            apvLineDTO.forEach(apvLine -> apvLine.setApvNo(savedApvForm.getApvNo()));

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태 확인 후 결재 상태 변경
            int approved = apvLineRepository.apvNoAllApproved(apvFormMain.getApvNo());
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvFormMain.getApvNo());
            }
            log.info("[ApprovalService] Exp6 insertApvFamilyEvent --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Exp6 insertApvFamilyEvent : " + e.getMessage());
            return false;
        }
    }



//
//    /* 전자결재 - 지출 : exp4 출장경비정산서 */
//    public List<ApvFormDTO> selectApvBusinessTripExp(int empNo, String title) {
//        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- start ");
//
//        List<ApvForm> apvBusinessTripList = approvalRepository.findByEmpNoAndTitle(empNo, title);
//        System.out.println("empNo = " + empNo);
//        System.out.println("title = " + title);
//        System.out.println("apvBusinessTripList = " + apvBusinessTripList);
//        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
//        return apvBusinessTripList.stream().map(apvForm -> modelMapper.map(apvForm, ApvFormDTO.class)).collect(Collectors.toList());
//    }
//

//
//    /* 전자결재 - 지출 : exp7 법인카드사용보고서 */
    @Transactional
    public Boolean insertApvCorpCard(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvCorpCard --------------- start ");
        log.info("[ApprovalHrmService] Exp7 insertApvCorpCard {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvCorpCardDTO> apvCorpCardDTO = apvFormDTO.getApvCorpCards();

            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);

            ApvFormMain savedApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(savedApvForm.getApvNo());

            apvCorpCardDTO.forEach(apvCorpCard -> apvCorpCard.setApvNo(savedApvForm.getApvNo()));

            List<ApvCorpCard> apvCorpCardList = apvCorpCardDTO.stream()
                    .map(item -> {
                        ApvCorpCard apvCorpCard = modelMapper.map(item, ApvCorpCard.class);
                        apvCorpCard.setApvNo(savedApvForm.getApvNo());
                        return apvCorpCard;
                    })
                    .collect(Collectors.toList());

            List<ApvCorpCard> savedApvCorpCardList = apvCorpCardRepository.saveAll(apvCorpCardList);

            IntStream.range(0, apvForm.getApvCorpCards().size())
                    .forEach(i -> {
                        ApvCorpCard apvCorpCardToUpdate = apvForm.getApvCorpCards().get(i);
                        apvCorpCardToUpdate.setApvNo(savedApvCorpCardList.get(i).getApvNo());
                        apvForm.getApvCorpCards().set(i, apvCorpCardToUpdate);
                    });

            apvLineDTO.forEach(apvLine -> apvLine.setApvNo(savedApvForm.getApvNo()));

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태 확인 후 결재 상태 변경
            int approved = apvLineRepository.apvNoAllApproved(apvFormMain.getApvNo());
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvFormMain.getApvNo());
            }
            log.info("[ApprovalService] Exp6 insertApvCorpCard --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Exp6 insertApvCorpCard : " + e.getMessage());
            return false;
        }
    }

}