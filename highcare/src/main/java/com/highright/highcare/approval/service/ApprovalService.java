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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ApprovalService {

    private final ApvFormRepository apvFormRepository;
    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvLineRepository apvLineRepository;
    private final ApvMeetingLogRepository apvMeetingLogRepository;
    private final ApvBusinessTripRepository apvBusinessTripRepository;
    private final ApvExpFormRepository apvExpFormRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ApprovalService(ModelMapper modelMapper,
                           ApvFormRepository apvFormRepository,
                           ApvFormMainRepository apvFormMainRepository,
                           ApvLineRepository apvLineRepository,
                           ApvMeetingLogRepository apvMeetingLogRepository,
                           ApvBusinessTripRepository apvBusinessTripRepository,
                           ApvExpFormRepository apvExpFormRepository
    ) {
        this.modelMapper = modelMapper;
        this.apvFormRepository = apvFormRepository;
        this.apvFormMainRepository = apvFormMainRepository;
        this.apvLineRepository = apvLineRepository;
        this.apvMeetingLogRepository = apvMeetingLogRepository;
        this.apvBusinessTripRepository = apvBusinessTripRepository;
        this.apvExpFormRepository = apvExpFormRepository;
    }

    /* Apv메인페이지 - 조건별 현황 */
    public Map<String, Integer> selectApvMainCount(int empNo) {
        log.info("[ApprovalService] selectApvMainCount --------------- start ");

        // 1. 오늘 - 결재진행중
        int countTodayInProgress = apvFormMainRepository.countByEmpNoAndApvStatus1(empNo);

        // 1. 오늘 - 긴급
        int countTodayUrgency = apvFormMainRepository.countByEmpNoAndIsUrgencyToday(empNo);

        // 2. 결재진행중
        int countInProgress = apvFormMainRepository.countByEmpNoAndApvStatus(empNo, "결재진행중");

        // 3. 긴급수신
        int countUrgency = apvFormMainRepository.countByEmpNoAndIsUrgency(empNo, "T");

        // 4. 신규수신
        int countNewReceive = apvFormMainRepository.countByEmpNoAndIsApprovalReceive(empNo, "F");

        // 2. 결재반려
        int countRejected = apvFormMainRepository.countByEmpNoAndApvStatus(empNo, "결재반려");


        Map<String, Integer> counts = new HashMap<>();
        counts.put("countTodayInProgress", countTodayInProgress);
        counts.put("countTodayUrgency", countTodayUrgency);

        counts.put("countInProgress", countInProgress);
        counts.put("countUrgency", countUrgency);
        counts.put("countNewReceive", countNewReceive);
        counts.put("countRejected", countRejected);

        log.info("[ApprovalService] selectApvMainCount --------------- end ");
        return counts;
    }

    /* Apv메인페이지 - 리스트 */
    public List<ApvFormMainDTO> selectMyApvList(int empNo) {
        log.info("[ApprovalService] selectMyApvList --------------- start ");

        List<ApvFormMain> writeApvList = apvFormMainRepository.findTitlesByEmpNo(empNo);
        writeApvList.forEach(ApvFormMain::getEmployee);
        log.info("[ApprovalService] selectMyApvList --------------- end ");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    /* 전자결재 결재함 조회 */
    public List<ApvFormMainDTO> selectWriteApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- start ");

        System.out.println("empNo = " + empNo);
        List<ApvFormMain> writeApvList = apvFormMainRepository.findByEmpNoAndApvStatusOrderByWriteDateDesc(empNo, apvStatus);

        System.out.println("writeApvList = " + writeApvList);

        writeApvList.forEach(ApvFormMain::getEmployee);
        System.out.println("WriteBox-writeApvList = " + writeApvList);
        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }

    /* 전자결재 수신함 조회 */
    public List<ApvFormMainDTO> selectReceiveApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectReceiveApvStatusApvList --------------- start ");

        String isApproval = "";
        List<ApvFormMain> receiveApvList;

        if (apvStatus.equals("결재진행중") || apvStatus.equals("결재완료")) {
            if (apvStatus.equals("결재진행중")) {
                isApproval = "F";
            } else if (apvStatus.equals("결재완료")) {
                isApproval = "T";
            }
            receiveApvList = apvFormMainRepository.findByEmpNoAndApvStatus2(empNo, isApproval);

        } else {
            receiveApvList = apvFormMainRepository.findByEmpNoAndApvStatus3(empNo, apvStatus);

        }

        System.out.println("ReceiveBox-receiveApvList = " + receiveApvList);

        receiveApvList.forEach(ApvFormMain::getEmployee);

        System.out.println("ReceiveBox-receiveApvList ============= " + receiveApvList);

        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
        return receiveApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    /* 전자결재 조회 - 페이징 */
    public int selectApvStatusTotal(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectApvStatusTotal --------------- start ");

        List<ApvFormMain> apvList = apvFormMainRepository.findByEmpNoAndApvStatusOrderByWriteDateDesc(empNo, apvStatus);

        log.info("[ApprovalService] selectApvStatusTotal --------------- end ");

        return apvList.size();
    }

    public Object selectListWithPaging(int empNo, String apvStatus, Criteria criteria) {
        log.info("[ApprovalService] selectListWithPaging => Start =============");

        int index = criteria.getPageNum() - 1;
        int count = criteria.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("apvNo").descending());

        Page<ApvFormMain> result1 = apvFormMainRepository.findByEmpNoAndApvStatusOrderByWriteDateDesc(empNo, apvStatus, paging);
        List<ApvFormMain> writeApvList = (List<ApvFormMain>) result1.getContent();
        writeApvList.forEach(ApvFormMain::getEmployee);

        log.info("[ApprovalService] selectListWithPaging => end =============");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    /* 전자결재 - 지출 : exp1 지출결의서, exp4 출장경비정산서 */
    @Transactional
    public Boolean insertApvExpense(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvExpense --------------- start ");

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvLineDTO> apvLineDTOs = apvFormWithLinesDTO.getApvLineDTOs();

            List<ApvExpFormDTO> apvExpFormDTO = apvFormDTO.getApvExpForms();
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            // 1. apvForm만 등록하기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);

            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(updateApvForm.getApvNo());

            // 2. ApvLine 등록하기

            List<ApvLine> apvLineList = apvLineDTOs.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());

            apvLineList.forEach(item -> {
                item.setApvNo(updateApvForm.getApvNo());
            });
            updateApvForm.setApvLines(apvLineList);
            apvForm.setApvLines(apvLineList);

            // 3. ApvExpForm 등록하기

            List<ApvExpForm> expFormList = apvExpFormDTO.stream()
                    .map(item -> {
                        ApvExpForm expForm = modelMapper.map(item, ApvExpForm.class);
                        expForm.setApvNo(updateApvForm.getApvNo());
                        return expForm;
                    })
                    .collect(Collectors.toList());

            List<ApvExpForm> savedExpFormList = apvExpFormRepository.saveAll(expFormList);

            IntStream.range(0, apvForm.getApvExpForms().size())
                    .forEach(i -> {
                        ApvExpForm apvExpFormToUpdate = apvForm.getApvExpForms().get(i);
                        apvExpFormToUpdate.setItemsNo(savedExpFormList.get(i).getItemsNo());
                        apvExpFormToUpdate.setApvNo(savedExpFormList.get(i).getApvNo());
                        apvForm.getApvExpForms().set(i, apvExpFormToUpdate);
                    });

            log.info("[ApprovalService] insertApvExpense --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error insertApvExpense : " + e.getMessage());
            return false;
        }
    }

    // 결재 승인
    @Transactional
    public boolean updateApprovalStatus(Long apvLineNo, Long apvNo) {
        log.info("[ApprovalService] updateApprovalStatus --------------- start ");
        try {

            apvLineRepository.updateIsApproval(apvLineNo);
            int approved = apvLineRepository.areAllApproved(apvLineNo);
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            } else if (approved >= 0) {
                apvFormRepository.updateApvStatusToProcess(apvNo);
            }
            log.info("[ApprovalService] updateApprovalStatus --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error updateApprovalStatus : " + e.getMessage());
            return false;
        }
    }

    // 결재 반려
    @Transactional
    public boolean updateApvStatusReject(Long apvNo) {
        log.info("[ApprovalService] updateApvStatusReject --------------- start ");
        try {
            apvFormRepository.updateApvStatusToReject(apvNo);
            apvFormRepository.updateIsApprovalToFalse(apvNo);

            log.info("[ApprovalService] updateApvStatusReject --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error updateApvStatusReject : " + e.getMessage());
            return false;
        }
    }

    // 기안 취소(삭제)
    @Transactional
    public Boolean deleteApvForm(Long apvNo) {
        log.info("[ApprovalService] deleteApvForm --------------- start ");
        try {
            apvLineRepository.deleteByApvNo(apvNo);
            apvFormRepository.deleteById(apvNo);
            log.info("[ApprovalService] deleteApvForm --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error deleteApvForm : " + e.getMessage());
            return false;
        }

    }


    // 기안 조회

    public ApvFormDTO searchApvFormWithLines(Long apvNo) {
        log.info("[ApprovalService] biz1-searchApvFormWithLines --------------- start ");

        ApvForm apvForm = apvFormRepository.findByApvNo(apvNo);
        apvForm.getApvLines().forEach(ApvLine::getEmployee);
        apvForm.getEmployee();

        if (apvForm == null) {
            log.error("[ApprovalService] Error: ApvForm not found with apvNo {}", apvNo);
            return null;
        }

        ApvFormDTO apvFormDTO = modelMapper.map(apvForm, ApvFormDTO.class);


        System.out.println("apvFormDTO = " + apvFormDTO);

        log.info("[ApprovalService] biz1-searchApvFormWithLines --------------- end ");
        return apvFormDTO;
    }
}


//
//    /* 전자결재 - 지출 : exp1 지출결의서, exp4 출장경비정산서 */
//    @Transactional
//    public Object insertApvExpense(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//        log.info("[ApprovalService] insertApvExpense --------------- start ");
//
//        try {
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            if (apvFormDTO.getApvExpForms() != null) {
//                List<ApvExpForm> apvExpForms = new ArrayList<>();
//                for (ApvExpFormDTO expFormDTO : apvFormDTO.getApvExpForms()) {
//                    ApvExpForm apvExpForm = modelMapper.map(expFormDTO, ApvExpForm.class);
//                    apvExpForm.setApvNo(apvForm.getApvNo());
//                    apvExpForms.add(apvExpForm);
//                }
//                apvForm.setApvExpForms(apvExpForms);
//            }
//
//            approvalRepository.save(apvForm);
//
//            log.info("[ApprovalService] insertApvExpense --------------- end ");
//            return "기안 상신 성공";
//        } catch (Exception e){
//            log.error("[ApprovalService] Error insertApvForm : " + e.getMessage());
//            return "기안 상신 실패";
//        }
//    }
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
//
//    /* 전자결재 - 지출 : exp6 경조금 신청서 */
//    @Transactional
//    public Object insertApvFamilyEvent(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//        log.info("[ApprovalService] insertApvFamilyEvent --------------- start ");
//
//        try {
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            if (apvFormDTO.getApvFamilyEvents() != null) {
//                List<ApvFamilyEvent> apvFamilyEvents = new ArrayList<>();
//                for (ApvFamilyEventDTO familyEventDTO : apvFormDTO.getApvFamilyEvents()) {
//                    ApvFamilyEvent apvFamilyEvent = modelMapper.map(familyEventDTO, ApvFamilyEvent.class);
//                    apvFamilyEvent.setApvNo(apvForm.getApvNo());
//                    apvFamilyEvents.add(apvFamilyEvent);
//                }
//                apvForm.setApvFamilyEvents(apvFamilyEvents);
//            }
//
//            approvalRepository.save(apvForm);
//
//            log.info("[ApprovalService] insertApvFamilyEvent --------------- end ");
//            return "기안 상신 성공";
//        } catch (Exception e){
//            log.error("[ApprovalService] Error insertApvFamilyEvent : " + e.getMessage());
//            return "기안 상신 실패";
//        }
//    }
//
//    /* 전자결재 - 지출 : exp7 법인카드사용보고서 */
//    @Transactional
//    public Object insertApvCorpCard(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//        log.info("[ApprovalService] insertApvCorpCard --------------- start ");
//
//        try {
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            if (apvFormDTO.getApvCorpCards()!= null) {
//                List<ApvCorpCard> apvCorpCards = new ArrayList<>();
//                for (ApvCorpCardDTO corpCardDTO : apvFormDTO.getApvCorpCards()) {
//                    ApvCorpCard apvCorpCard = modelMapper.map(corpCardDTO, ApvCorpCard.class);
//                    apvCorpCard.setApvNo(apvForm.getApvNo());
//                    apvCorpCards.add(apvCorpCard);
//                }
//                apvForm.setApvCorpCards(apvCorpCards);
//            }
//
//            approvalRepository.save(apvForm);
//
//            log.info("[ApprovalService] insertApvCorpCard --------------- end ");
//            return "기안 상신 성공";
//        } catch (Exception e){
//            log.error("[ApprovalService] Error insertApvCorpCard : " + e.getMessage());
//            return "기안 상신 실패";
//        }
//    }
//
//
//
//
//
