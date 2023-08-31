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
public class ApprovalBizService {

    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvMeetingLogRepository apvMeetingLogRepository;
    private final ApvBusinessTripRepository apvBusinessTripRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ApprovalBizService(ModelMapper modelMapper,
                              ApvFormMainRepository apvFormMainRepository,
                              ApvMeetingLogRepository apvMeetingLogRepository,
                              ApvBusinessTripRepository apvBusinessTripRepository
    ) {
        this.modelMapper = modelMapper;
        this.apvFormMainRepository = apvFormMainRepository;
        this.apvMeetingLogRepository = apvMeetingLogRepository;
        this.apvBusinessTripRepository = apvBusinessTripRepository;
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

            log.info("[ApprovalService] insertApvForm --------------- end ");
            return true;
        } catch (Exception e) {
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
}