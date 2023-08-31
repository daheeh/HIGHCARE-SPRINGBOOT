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
        int countRejected = apvFormMainRepository.countByEmpNoAndApvStatus(empNo, "반려");



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

        log.info("[ApprovalService] selectMyApvList --------------- end ");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    /* 전자결재 결재함 조회 */
    public List<ApvFormMainDTO> selectWriteApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- start ");

        List<ApvFormMain> writeApvList = apvFormMainRepository.findByEmpNoAndApvStatus(empNo, apvStatus);

        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }

    /* 전자결재 수신함 조회 */
    public List<ApvFormMainDTO> selectReceiveApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectReceiveApvStatusApvList --------------- start ");

        List<ApvFormMain> receiveApvList = apvFormMainRepository.findByEmpNoAndApvStatus2(empNo, apvStatus);

        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- end ");
        return receiveApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    /* 전자결재 조회 - 페이징 */
    public int selectApvStatusTotal(int empNo, String apvStatus){
        log.info("[ApprovalService] selectApvStatusTotal --------------- start ");

        List<ApvFormMain> apvList = apvFormMainRepository.findByEmpNoAndApvStatus(empNo, apvStatus);

        log.info("[ApprovalService] selectApvStatusTotal --------------- end ");

        return apvList.size();
    }
    public Object selectListWithPaging(int empNo, String apvStatus, Criteria criteria) {
        log.info("[ApprovalService] selectListWithPaging => Start =============");

        int index = criteria.getPageNum() - 1;
        int count = criteria.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("apvNo").descending());

        Page<ApvFormMain> result1 = apvFormMainRepository.findByEmpNoAndApvStatus(empNo, apvStatus, paging);
        List<ApvFormMain> writeApvList = (List<ApvFormMain>) result1.getContent();

        log.info("[ApprovalService] selectListWithPaging => end =============");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }

    /* 전자결재 - 업무 : biz1 기안서 */
    @Transactional
    public Boolean insertApvFormWithLines(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvForm --------------- start ");
        log.info("[ApprovalService] apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(updateApvForm.getApvNo());

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());

            apvLineList.forEach(item -> {
                item.setApvNo(updateApvForm.getApvNo());
            });
            updateApvForm.setApvLines(apvLineList);
            apvForm.setApvLines(apvLineList);

            log.info("[ApprovalService] insertApvForm --------------- end ");
            return true;
        } catch (Exception e){
            log.error("[ApprovalService] Error insertApvForm : " + e.getMessage());
            return false;
        }
    }

    /* 전자결재 - 업무 : biz2 회의록 */
    @Transactional
    public Boolean insertApvMeetingLog(ApvFormWithLinesDTO apvFormWithLinesDTO) {

        log.info("[ApprovalService] insertApvMeetingLog --------------- start ");
        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            List<ApvMeetingLogDTO> apvMeetingLogsDTO = apvFormDTO.getApvMeetingLogs();

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);

            // 1. apvForm만 등록하기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(updateApvForm.getApvNo());

            // 2. ApvLine 등록하기

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());

            apvLineList.forEach(item -> {
                item.setApvNo(updateApvForm.getApvNo());
            });
            updateApvForm.setApvLines(apvLineList);
            apvForm.setApvLines(apvLineList);

            // 3. ApvMeetingLog 등록하기

            List<ApvMeetingLog> meetingLogList = apvMeetingLogsDTO.stream()
                    .map(item -> {
                        ApvMeetingLog meetingLog = modelMapper.map(item, ApvMeetingLog.class);
                        meetingLog.setApvNo(updateApvForm.getApvNo());
                        return meetingLog;
                    })
                    .collect(Collectors.toList());

            List<ApvMeetingLog> savedMeetingLogList = apvMeetingLogRepository.saveAll(meetingLogList);

            IntStream.range(0, apvForm.getApvMeetingLogs().size())
            .forEach(i -> {
                ApvMeetingLog apvMeetingLogToUpdate = apvForm.getApvMeetingLogs().get(i);
                System.out.println("apvForm.getApvMeetingLogs().get(" + i + ") = " + apvMeetingLogToUpdate);

                apvMeetingLogToUpdate.setItemsNo(savedMeetingLogList.get(i).getItemsNo());
                apvMeetingLogToUpdate.setApvNo(savedMeetingLogList.get(i).getApvNo());

                apvForm.getApvMeetingLogs().set(i, apvMeetingLogToUpdate);
                System.out.println("apvMeetingLogToUpdate = " + apvForm.getApvMeetingLogs().get(i));
            });
            log.info("[ApprovalService] insertApvMeetingLog --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error insertApvMeetingLog : " + e.getMessage());
            return false;
        }
    }

    /* 전자결재 - 업무 : biz3 출장신청서 */
    @Transactional
    public Boolean insertApvBusinessTrip(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvBusinessTrip --------------- start ");

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvLineDTO> apvLineDTOs = apvFormWithLinesDTO.getApvLineDTOs();

            List<ApvBusinessTripDTO> apvBusinessTripDTO = apvFormDTO.getApvBusinessTrips();
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

            // 3. apvBusinessTrips 등록하기

            List<ApvBusinessTrip> businessTripList = apvBusinessTripDTO.stream()
                    .map(item -> {
                        ApvBusinessTrip businessTrip = modelMapper.map(item, ApvBusinessTrip.class);
                        businessTrip.setApvNo(updateApvForm.getApvNo());
                        return businessTrip;
                    })
                    .collect(Collectors.toList());

            List<ApvBusinessTrip> savedBusinessTripList = apvBusinessTripRepository.saveAll(businessTripList);

            IntStream.range(0, apvForm.getApvBusinessTrips().size())
                    .forEach(i -> {
                        ApvBusinessTrip apvBusinessTripToUpdate = apvForm.getApvBusinessTrips().get(i);
                        apvBusinessTripToUpdate.setItemsNo(savedBusinessTripList.get(i).getItemsNo());
                        apvBusinessTripToUpdate.setApvNo(savedBusinessTripList.get(i).getApvNo());
                        apvForm.getApvBusinessTrips().set(i, apvBusinessTripToUpdate);
                    });

            log.info("[ApprovalService] insertApvMeetingLog --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error insertApvMeetingLog : " + e.getMessage());
            return false;
        }
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
//    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
//    @Transactional
//    public Object insertApvVacation(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//
//        log.info("[ApprovalService] insertApvVacation --------------- start ");
//
//        try {
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            if (apvFormDTO.getApvVacations() != null) {
//                List<ApvVacation> apvVacations = new ArrayList<>();
//                for (ApvVacationDTO vacationDTO : apvFormDTO.getApvVacations()) {
//                    ApvVacation apvVacation = modelMapper.map(vacationDTO, ApvVacation.class);
//                    apvVacation.setApvNo(apvForm.getApvNo());
//                    apvVacations.add(apvVacation);
//                }
//                apvForm.setApvVacations(apvVacations);
//            }
//
//            approvalRepository.save(apvForm);
//
//            log.info("[ApprovalService] insertApvVacation --------------- end ");
//            return "기안 상신 성공";
//        } catch (Exception e){
//            log.error("[ApprovalService] Error insertApvVacation : " + e.getMessage());
//            return "기안 상신 실패";
//        }
//    }
//
//    /* 전자결재 - 인사 : hrm3 서류발급신청서 */
//    @Transactional
//    public Object insertApvIssuance(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//
//        log.info("[ApprovalService] insertApvIssuance --------------- start ");
//
//        try {
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            if (apvFormDTO.getApvIssuances() != null) {
//                List<ApvIssuance> apvIssuances = new ArrayList<>();
//                for (ApvIssuanceDTO apvIssuanceDTO : apvFormDTO.getApvIssuances()) {
//                    ApvIssuance apvIssuance = modelMapper.map(apvIssuanceDTO, ApvIssuance.class);
//                    apvIssuance.setApvNo(apvForm.getApvNo());
//                    apvIssuances.add(apvIssuance);
//                }
//                apvForm.setApvIssuances(apvIssuances);
//            }
//
//            approvalRepository.save(apvForm);
//
//            log.info("[ApprovalService] insertApvIssuance --------------- end ");
//            return "기안 상신 성공";
//        } catch (Exception e){
//            log.error("[ApprovalService] Error insertApvIssuance : " + e.getMessage());
//            return "기안 상신 실패";
//        }
//    }


}

//    @Transactional
//    public Boolean insertApvBusinessTrip(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//        log.info("[ApprovalService] insertApvBusinessTrip --------------- start ");
//        log.info("[ApprovalService] insertApvBusinessTrip {}", apvFormWithLinesDTO);
//
//        try {
//            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
//            List<ApvLineDTO> apvLineDTOs = apvFormWithLinesDTO.getApvLineDTOs();
//
//            List<ApvBusinessTripDTO> apvBusinessTripDTO = apvFormDTO.getApvBusinessTrips();
//
//            System.out.println("1  apvFormDTO ======= " + apvFormDTO);
//            System.out.println("1  apvLineDTOs ======= " + apvLineDTOs);
//            System.out.println("1  meetingLogDTOs ======= " + apvBusinessTripDTO);
//            System.out.println("=========================================================");
//
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            // 1. apvForm만 등록하기
//            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
//            System.out.println("2-1  apvFormMain ======= " + apvFormMain);
//
//            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
//            System.out.println("/////// updateApvForm = " + updateApvForm);
//
//            System.out.println("updateApvForm.getApvNo() = " + updateApvForm.getApvNo());
//            System.out.println("2-1-2  apvFormMain  ======= " + apvFormMain);
//            apvForm.setApvNo(updateApvForm.getApvNo());
//            System.out.println("     2-1-  apvForm = " + apvForm);
//            System.out.println("=========================================================");
//
//
//            // 2. ApvLine 등록하기
//
//            List<ApvLine> apvLineList = apvLineDTOs.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
//            System.out.println("2-2 apvLineList ======= " + apvLineList);
//
//            apvLineList.forEach(item -> {
//                item.setApvNo(updateApvForm.getApvNo());
//                System.out.println("item = " + item);
//            });
//            updateApvForm.setApvLines(apvLineList);
//
//            System.out.println("2-2-2 apvLineList ======= " + apvLineList);
//            List<ApvLine> savedApvLineList = apvLineRepository.saveAll(apvLineList);
//            System.out.println("2-2-2 savedApvLineList ======= " + savedApvLineList);
//
//            apvForm.setApvLines(apvLineList);
//            System.out.println("     2-2-  apvForm = " + apvForm);
//
//            System.out.println("=========================================================");
//
//
//            // 3. apvBusinessTrips 등록하기
//
//            List<ApvBusinessTrip> businessTripList = apvBusinessTripDTO.stream()
//                    .map(item -> {
//                        ApvBusinessTrip businessTrip = modelMapper.map(item, ApvBusinessTrip.class);
//                        businessTrip.setApvNo(updateApvForm.getApvNo());
//                        return businessTrip;
//                    })
//                    .collect(Collectors.toList());
//
//            System.out.println("2-3-1 meetingLogList ======= " + businessTripList);
//
//            List<ApvBusinessTrip> savedBusinessTripList = apvBusinessTripRepository.saveAll(businessTripList);
//            System.out.println("2-3-2 savedMeetingLogList ======= " + savedBusinessTripList);
//            System.out.println("     2-3-  pre apvForm = " + apvForm);
//
//            IntStream.range(0, apvForm.getApvBusinessTrips().size())
//                    .forEach(i -> {
//                        ApvBusinessTrip apvBusinessTripToUpdate = apvForm.getApvBusinessTrips().get(i);
//                        System.out.println("apvForm.getApvMeetingLogs().get(" + i + ") = " + apvBusinessTripToUpdate);
//
//                        apvBusinessTripToUpdate.setItemsNo(savedBusinessTripList.get(i).getItemsNo());
//                        apvBusinessTripToUpdate.setApvNo(savedBusinessTripList.get(i).getApvNo());
//
//                        apvForm.getApvBusinessTrips().set(i, apvBusinessTripToUpdate);
//                        System.out.println("apvMeetingLogToUpdate = " + apvForm.getApvBusinessTrips().get(i));
//                    });
//
//            System.out.println("     2-3-  apvForm = " + apvForm);
//
//            System.out.println("=========================================================");
//
//            log.info("[ApprovalService] insertApvMeetingLog --------------- end ");
//            return true;
//        } catch (Exception e) {
//            log.error("[ApprovalService] Error insertApvMeetingLog : " + e.getMessage());
//            return false;
//        }
//    }
