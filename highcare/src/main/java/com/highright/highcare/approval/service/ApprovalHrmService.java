package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.*;
import com.highright.highcare.approval.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalHrmService {

    private final ModelMapper modelMapper;
    private final ApprovalService approvalService;
    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvFormRepository apvFormRepository;
    private final ApvLineRepository apvLineRepository;
    private final ApvVacationRepository apvVacationRepository;
    private final ApvIssuanceRepository apvIssuanceRepository;

    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
    @Transactional
    public Boolean insertApvVacation(ApvFormDTO apvFormDTO, List<ApvLineDTO> apvLineDTOs, List<MultipartFile> apvFileDTO) {
        log.info("[ApprovalService] Hrm1-insertApvVacation --------------- 연차신청서 상신 start ");
        log.info("[ApprovalService] apvFormDTO {}", apvFormDTO);
        log.info("[ApprovalService] apvLineDTOs {}", apvLineDTOs);
        log.info("[ApprovalService] apvFileDTO {}", apvFileDTO);

        try {
            List<ApvVacationDTO> apvVacationDTO = apvFormDTO.getApvVacations();

            // ApvFormMain을 저장하고 생성된 ApvNo를 가져오기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvVacationDTO를 ApvVacation 엔티티로 매핑하고 ApvNo를 설정
            List<ApvVacation> apvVacationList = apvVacationDTO.stream()
                    .map(item -> {
                        ApvVacation apvVacation = modelMapper.map(item, ApvVacation.class);
                        apvVacation.setApvNo(apvNo);
                        return apvVacation;
                    })
                    .collect(Collectors.toList());

            // ApvVacation 엔티티를 저장
            apvVacationList = apvVacationRepository.saveAll(apvVacationList);

            // ApvLineDTO를 ApvLine 엔티티로 매핑하고 ApvNo를 설정
            List<ApvLine> apvLineList = apvLineDTOs.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // 첨부파일 등록을 위해 서비스로 DTO전달
            List<ApvFile> apvFiles = new ArrayList<>();
            if (apvFileDTO != null && !apvFileDTO.isEmpty()) {
                apvFiles = approvalService.insertFiles(apvFormMain.getApvNo(), apvFileDTO);
            }
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFiles = " + apvFiles);

            // ApvLine, ApvFile 엔티티를 ApvFormMain에 설정
            apvFormMain.setApvLines(apvLineList);
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFormMain = " + apvFormMain);

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Hrm1 insertApvVacation --------------- 연차신청서 상신 end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Hrm1 insertApvVacation : " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public Boolean updateApvVacation(Long apvNo, ApvFormDTO apvFormDTO, List<ApvLineDTO> apvLineDTOs, List<MultipartFile> apvFileDTO) {
        log.info("[ApprovalService] Hrm1-updateApvVacation --------------- 연차 신청 업데이트 시작 ");
        log.info("[ApprovalService] apvFormDTO {}", apvFormDTO);
        log.info("[ApprovalService] apvLineDTOs {}", apvLineDTOs);
        log.info("[ApprovalService] apvFileDTO {}", apvFileDTO);

        try {

            List<ApvVacationDTO> apvVacationDTOList = apvFormDTO.getApvVacations();

            // 기존 ApvFormMain 검색
            Optional<ApvFormMain> savedApvFormMain = apvFormMainRepository.findById(apvNo);

            // ApvForm 및 ApvFormMain 생성 및 저장
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);

            // ApvVacation 엔터티 업데이트
            List<ApvVacation> apvVacationList = apvVacationDTOList.stream()
                    .map(item -> {
                        ApvVacation apvVacation = modelMapper.map(item, ApvVacation.class);
                        apvVacation.setApvNo(apvNo);
                        return apvVacation;
                    })
                    .collect(Collectors.toList());

            // ApvVacation 엔티티 저장
            apvVacationList = apvVacationRepository.saveAll(apvVacationList);

            // ApvLine 엔터티 업데이트
            List<ApvLine> apvLineList = apvLineDTOs.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // ApvFile 엔터티 업데이트
            List<ApvFile> apvFiles = new ArrayList<>();
            if (apvFileDTO != null && !apvFileDTO.isEmpty()) {
                apvFiles = approvalService.updateFiles(apvNo, apvFileDTO);
            }
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFiles = " + apvFiles);

            // ApvFormMain에 ApvLine 및 ApvFile 엔터티 설정
            apvFormMain.setApvLines(apvLineList);
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFormMain = " + apvFormMain);

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Hrm1 updateApvVacation --------------- 연차 신청 업데이트 종료 ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] 오류: Hrm1 updateApvVacation : " + e.getMessage());
            return false;
        }
    }



    /* 전자결재 - 인사 : hrm3 서류발급신청서 */

    @Transactional
    public Boolean insertApvIssuance(ApvFormDTO apvFormDTO, List<ApvLineDTO> apvLineDTOs, List<MultipartFile> apvFileDTO) {
        log.info("[ApprovalService] Hrm3-insertApvVacation --------------- 서류발급신청서 상신 start ");
        log.info("[ApprovalService] apvFormDTO {}", apvFormDTO);
        log.info("[ApprovalService] apvLineDTOs {}", apvLineDTOs);
        log.info("[ApprovalService] apvFileDTO {}", apvFileDTO);


        try {
            List<ApvIssuanceDTO> apvIssuanceDTO = apvFormDTO.getApvIssuances();

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
            List<ApvLine> apvLineList = apvLineDTOs.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // 첨부파일 등록을 위해 서비스로 DTO전달
            List<ApvFile> apvFiles = new ArrayList<>();
            if (apvFileDTO != null && !apvFileDTO.isEmpty()) {
                apvFiles = approvalService.insertFiles(apvFormMain.getApvNo(), apvFileDTO);
            }
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFiles = " + apvFiles);

            // ApvLine, ApvFile 엔티티를 ApvFormMain에 설정
            apvFormMain.setApvLines(apvLineList);
            apvFormMain.setApvFiles(apvFiles);
            System.out.println("apvFormMain = " + apvFormMain);

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Hrm3 insertApvIssuance --------------- 서류발급신청서 상신 end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Hrm3 insertApvIssuance : " + e.getMessage());
            return false;
        }
    }

}