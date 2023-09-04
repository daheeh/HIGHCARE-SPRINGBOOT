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

    private final ModelMapper modelMapper;
    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvFormRepository apvFormRepository;
    private final ApvLineRepository apvLineRepository;
    private final ApvVacationRepository apvVacationRepository;
    private final ApvIssuanceRepository apvIssuanceRepository;


    @Autowired
    public ApprovalHrmService(ModelMapper modelMapper,
                              ApvFormMainRepository apvFormMainRepository,
                              ApvFormRepository apvFormRepository,
                              ApvLineRepository apvLineRepository,
                              ApvVacationRepository apvVacationRepository,
                              ApvIssuanceRepository apvIssuanceRepository
    ) {
        this.modelMapper = modelMapper;
        this.apvFormMainRepository = apvFormMainRepository;
        this.apvFormRepository = apvFormRepository;
        this.apvLineRepository = apvLineRepository;
        this.apvVacationRepository = apvVacationRepository;
        this.apvIssuanceRepository = apvIssuanceRepository;

    }


    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
    @Transactional
    public Boolean insertApvVacation(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalHrmService] hrm1-insertApvVacation --------------- start ");
        log.info("[ApprovalHrmService] hrm1 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            // ApvFormWithLinesDTO에서 필요한 데이터 추출
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvVacationDTO> apvVacationDTO = apvFormDTO.getApvVacations();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            // ApvFormMain을 저장하고 생성된 ApvNo를 가져오기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvVacationDTO를 ApvVacation 엔티티로 매핑하고 ApvNo를 설정
            List<ApvVacation> apvVacationList = apvVacationDTO.stream()
                    .map(item -> {
                        ApvVacation apvVacation = modelMapper.map(item, ApvVacation.class);
                        apvVacation.setApvNo(apvNo);
//                        apvVacation.getApvForm().setApvNo(apvNo);
                        return apvVacation;
                    })
                    .collect(Collectors.toList());

            // ApvVacation 엔티티를 저장
            apvVacationList = apvVacationRepository.saveAll(apvVacationList);

            // ApvLineDTO를 ApvLine 엔티티로 매핑하고 ApvNo를 설정
            List<ApvLine> apvLineList = apvLineDTO.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // ApvLine 엔티티를 ApvFormMain에 설정
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] hrm1 insertApvVacation --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error hrm1 insertApvVacation : " + e.getMessage());
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


    /* 전자결재 - 인사 : hrm3 서류발급신청서 */

    @Transactional
    public Boolean insertApvIssuance(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalHrmService] hrm3-insertApvIssuance --------------- start ");
        log.info("[ApprovalHrmService] hrm3 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            // ApvFormWithLinesDTO에서 필요한 데이터 추출
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvIssuanceDTO> apvIssuanceDTO = apvFormDTO.getApvIssuances();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            // ApvFormMain을 저장하고 생성된 ApvNo를 가져오기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvIssuanceDTO를 ApvIssuance 엔티티로 매핑하고 ApvNo를 설정
            List<ApvIssuance> apvIssuanceList = apvIssuanceDTO.stream()
                    .map(item -> {
                        ApvIssuance apvIssuance = modelMapper.map(item, ApvIssuance.class);
                        apvIssuance.setApvNo(apvNo);
//                        apvIssuance.getApvForm().setApvNo(apvNo);
                        return apvIssuance;
                    })
                    .collect(Collectors.toList());

            // ApvIssuance 엔티티를 저장
            apvIssuanceList = apvIssuanceRepository.saveAll(apvIssuanceList);

            // ApvLineDTO를 ApvLine 엔티티로 매핑하고 ApvNo를 설정
            List<ApvLine> apvLineList = apvLineDTO.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // ApvLine 엔티티를 ApvFormMain에 설정
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] hrm3 insertApvIssuance --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error hrm3 insertApvIssuance : " + e.getMessage());
            return false;
        }
    }

}