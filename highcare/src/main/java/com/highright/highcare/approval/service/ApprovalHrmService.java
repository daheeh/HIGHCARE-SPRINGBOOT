package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.*;
import com.highright.highcare.approval.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ApprovalHrmService {

    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvFormRepository apvFormRepository;
    private final ApvLineRepository apvLineRepository;

    private final ApvVacationRepository apvVacationRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ApprovalHrmService(ModelMapper modelMapper,
                              ApvFormMainRepository apvFormMainRepository,
                              ApvFormRepository apvFormRepository,
                              ApvLineRepository apvLineRepository,
                              ApvVacationRepository apvVacationRepository
    ) {
        this.modelMapper = modelMapper;
        this.apvFormMainRepository = apvFormMainRepository;
        this.apvFormRepository = apvFormRepository;
        this.apvLineRepository = apvLineRepository;
        this.apvVacationRepository = apvVacationRepository;

    }


    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
    @Transactional
    public Boolean insertApvVacation(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalHrmService] hrm1-insertApvVacation --------------- start ");
        log.info("[ApprovalHrmService] hrm1 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            System.out.println("=========== 1. apvFormDTO ===========");
            System.out.println(apvFormDTO);

            List<ApvVacationDTO> apvVacationDTO = apvFormDTO.getApvVacations();
            System.out.println("=========== 2. apvVacationDTO ===========");
            System.out.println(apvVacationDTO);

            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();
            System.out.println("=========== 3. apvLineDTO ===========");
            System.out.println(apvLineDTO);

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            System.out.println("=========== 4. apvForm ===========");
            System.out.println(apvForm);


            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            System.out.println("=========== 5. apvFormMain ===========");
            System.out.println(apvFormMain);

            ApvFormMain savedApvForm = apvFormMainRepository.save(apvFormMain);
            apvForm.setApvNo(savedApvForm.getApvNo());
            System.out.println("=========== 5. savedApvForm ===========");
            System.out.println(savedApvForm);
            System.out.println("=========== 5. apvForm ===========");
            System.out.println(apvForm);


            apvVacationDTO.forEach(apvVacation -> apvVacation.setApvNo(savedApvForm.getApvNo()));
            System.out.println("=========== 6. apvVacationDTO ===========");
            System.out.println(apvVacationDTO);

            List<ApvVacation> apvVacationList = apvVacationDTO.stream()
                    .map(item ->{
                        ApvVacation apvVacation = modelMapper.map(item, ApvVacation.class);
                        apvVacation.setApvNo(savedApvForm.getApvNo());
                        return apvVacation;
                    })
                    .collect(Collectors.toList());


            List<ApvVacation> savedExpFormList = apvVacationRepository.saveAll(apvVacationList);

            IntStream.range(0, apvForm.getApvVacations().size())
                    .forEach(i -> {
                        ApvVacation apvApvVacationToUpdate = apvForm.getApvVacations().get(i);
                        apvApvVacationToUpdate.setApvNo(savedExpFormList.get(i).getApvNo());
                        apvForm.getApvVacations().set(i, apvApvVacationToUpdate);
                    });

            System.out.println("=========== 7. savedExpFormList ===========");
            System.out.println(savedExpFormList);

            System.out.println("=========== 7-2. apvVacationList ===========");
            System.out.println(apvVacationList);
            System.out.println("=========== 7. apvForm ===========");
            System.out.println(apvVacationList);

            apvLineDTO.forEach(apvLine -> apvLine.setApvNo(savedApvForm.getApvNo()));
            System.out.println("=========== 8. apvLineDTO ===========");
            System.out.println(apvLineDTO);

            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
            apvFormMain.setApvLines(apvLineList);

            System.out.println("=========== 9. apvLineList ===========");
            System.out.println(apvLineList);
            System.out.println("=========== 10. apvForm ===========");
            System.out.println(apvFormMain);
            System.out.println("=========== 11. savedApvForm ===========");
            System.out.println(savedApvForm);

            // 승인 상태 확인 후 결재 상태 변경
            int approved = apvLineRepository.apvNoAllApproved(apvFormMain.getApvNo());
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvFormMain.getApvNo());
            }
            log.info("[ApprovalHrmService] hrm1-insertApvVacation --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalHrmService] Error hrm1-insertApvVacation : " + e.getMessage());
            return false;
        }
    }


    @Transactional
    public Boolean putApvFormWithLines(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] biz1-putApvFormWithLines --------------- start ");
        log.info("[ApprovalService] apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            System.out.println("=========== 1. apvFormDTO ===========");
            System.out.println(apvFormDTO);

            List<ApvLineDTO> apvLineDTO = apvFormDTO.getApvLines();
            System.out.println("=========== 2. apvLineDTO ===========");
            System.out.println(apvLineDTO);

            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            System.out.println("=========== 3. apvForm ===========");
            System.out.println(apvForm);

            List<ApvLine> apvLineList = apvForm.getApvLines();
            System.out.println("apvLineList = " + apvLineList);

            ApvForm savedApvForm = apvFormRepository.save(apvForm);
            System.out.println("=========== 4. savedApvForm ===========");
            System.out.println(savedApvForm);



            log.info("[ApprovalService] biz1-putApvFormWithLines --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error biz1-putApvFormWithLines : " + e.getMessage());
            return false;
        }
    }



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







    /* 전자결재 - 업무 : biz2 회의록 */
//    @Transactional
//    public Boolean insertApvMeetingLog(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//
//        log.info("[ApprovalService] insertApvMeetingLog --------------- start ");
//        try {
//            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
//            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();
//
//            List<ApvMeetingLogDTO> apvMeetingLogsDTO = apvFormDTO.getApvMeetingLogs();
//
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            // 1. apvForm만 등록하기
//            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
//            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
//            apvForm.setApvNo(updateApvForm.getApvNo());
//
//            // 2. ApvLine 등록하기
//
//            List<ApvLine> apvLineList = apvLineDTO.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
//
//            apvLineList.forEach(item -> {
//                item.setApvNo(updateApvForm.getApvNo());
//            });
//            updateApvForm.setApvLines(apvLineList);
//            apvForm.setApvLines(apvLineList);
//
//            // 3. ApvMeetingLog 등록하기
//
//            List<ApvMeetingLog> meetingLogList = apvMeetingLogsDTO.stream()
//                    .map(item -> {
//                        ApvMeetingLog meetingLog = modelMapper.map(item, ApvMeetingLog.class);
//                        meetingLog.setApvNo(updateApvForm.getApvNo());
//                        return meetingLog;
//                    })
//                    .collect(Collectors.toList());
//
//            List<ApvMeetingLog> savedMeetingLogList = apvMeetingLogRepository.saveAll(meetingLogList);
//
//            IntStream.range(0, apvForm.getApvMeetingLogs().size())
//                    .forEach(i -> {
//                        ApvMeetingLog apvMeetingLogToUpdate = apvForm.getApvMeetingLogs().get(i);
//                        System.out.println("apvForm.getApvMeetingLogs().get(" + i + ") = " + apvMeetingLogToUpdate);
//
//                        apvMeetingLogToUpdate.setItemsNo(savedMeetingLogList.get(i).getItemsNo());
//                        apvMeetingLogToUpdate.setApvNo(savedMeetingLogList.get(i).getApvNo());
//
//                        apvForm.getApvMeetingLogs().set(i, apvMeetingLogToUpdate);
//                        System.out.println("apvMeetingLogToUpdate = " + apvForm.getApvMeetingLogs().get(i));
//                    });
//            log.info("[ApprovalService] insertApvMeetingLog --------------- end ");
//            return true;
//        } catch (Exception e) {
//            log.error("[ApprovalService] Error insertApvMeetingLog : " + e.getMessage());
//            return false;
//        }
//    }
//
//    /* 전자결재 - 업무 : biz3 출장신청서 */
//    @Transactional
//    public Boolean insertApvBusinessTrip(ApvFormWithLinesDTO apvFormWithLinesDTO) {
//        log.info("[ApprovalService] insertApvBusinessTrip --------------- start ");
//
//        try {
//            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
//            List<ApvLineDTO> apvLineDTOs = apvFormWithLinesDTO.getApvLineDTOs();
//
//            List<ApvBusinessTripDTO> apvBusinessTripDTO = apvFormDTO.getApvBusinessTrips();
//            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
//
//            // 1. apvForm만 등록하기
//            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
//
//            ApvFormMain updateApvForm = apvFormMainRepository.save(apvFormMain);
//            apvForm.setApvNo(updateApvForm.getApvNo());
//
//            // 2. ApvLine 등록하기
//
//            List<ApvLine> apvLineList = apvLineDTOs.stream().map(item -> modelMapper.map(item, ApvLine.class)).collect(Collectors.toList());
//
//            apvLineList.forEach(item -> {
//                item.setApvNo(updateApvForm.getApvNo());
//            });
//            updateApvForm.setApvLines(apvLineList);
//            apvForm.setApvLines(apvLineList);
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
//            List<ApvBusinessTrip> savedBusinessTripList = apvBusinessTripRepository.saveAll(businessTripList);
//
//            IntStream.range(0, apvForm.getApvBusinessTrips().size())
//                    .forEach(i -> {
//                        ApvBusinessTrip apvBusinessTripToUpdate = apvForm.getApvBusinessTrips().get(i);
//                        apvBusinessTripToUpdate.setItemsNo(savedBusinessTripList.get(i).getItemsNo());
//                        apvBusinessTripToUpdate.setApvNo(savedBusinessTripList.get(i).getApvNo());
//                        apvForm.getApvBusinessTrips().set(i, apvBusinessTripToUpdate);
//                    });
//
//            log.info("[ApprovalService] insertApvMeetingLog --------------- end ");
//            return true;
//        } catch (Exception e) {
//            log.error("[ApprovalService] Error insertApvMeetingLog : " + e.getMessage());
//            return false;
//        }
//    }


}