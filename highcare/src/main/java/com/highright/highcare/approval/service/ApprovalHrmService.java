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
    private final ApvFileRepository apvFileRepository;
    private final ApvVacationRepository apvVacationRepository;
    private final ApvIssuanceRepository apvIssuanceRepository;


    /*
     * Hrm1 : 연차신청서
     * Hrm2 : 기타휴가신청서
     * Hrm3 : 서류발급신청서
     * */


    /* 전자결재 - 인사 : Hrm1 연차신청서, Hrm2 기타휴가신청서 */
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

    /* 전자결재 - 인사 : Hrm1 연차신청서, Hrm2 기타휴가신청서  수정 */
    @Transactional
    public Boolean updateApvVacation(Long apvNo, ApvFormDTO apvFormDTO, List<ApvLineDTO> apvLineDTOs, List<MultipartFile> apvFileDTO) {
        log.info("[ApprovalService] Hrm1-updateApvVacation --------------- 연차 신청 업데이트 시작 ");
        log.info("[ApprovalService] apvFormDTO {}", apvFormDTO);
        log.info("[ApprovalService] apvLineDTOs {}", apvLineDTOs);
        log.info("[ApprovalService] apvFileDTO {}", apvFileDTO);
        try {
            // 기존 ApvForm을 검색
            ApvForm savedApvForm = apvFormRepository.findById(apvNo).orElse(null);
            ApvFormMain savedApvFormMain = apvFormMainRepository.findById(apvNo).orElse(null);

            savedApvFormMain.setTitle(apvFormDTO.getTitle());
            savedApvFormMain.setWriteDate(apvFormDTO.getWriteDate());
            savedApvFormMain.setIsUrgency(apvFormDTO.getIsUrgency());
            savedApvFormMain.setCategory(apvFormDTO.getCategory());
            savedApvFormMain.setContents1(apvFormDTO.getContents1());
            savedApvFormMain.setContents2(apvFormDTO.getContents2());

            apvFormMainRepository.save(savedApvFormMain);
            System.out.println("savedApvFormMain======================== = " + savedApvFormMain);
            System.out.println("============================================================ 1");

            apvVacationRepository.deleteByApvNo(apvNo);

            List<ApvVacationDTO> apvVacationDTO = apvFormDTO.getApvVacations();
            // ApvVacationDTO ApvVacation 엔티티로 매핑하고 ApvNo를 설정
            List<ApvVacation> apvVacationList = apvVacationDTO.stream()
                    .map(dto -> {
                        ApvVacation apvVacation = modelMapper.map(dto, ApvVacation.class);
                        apvVacation.setApvNo(apvNo);
                        return apvVacation;
                    })
                    .collect(Collectors.toList());

            // ApvVacation 엔티티를 저장
            apvVacationList = apvVacationRepository.saveAll(apvVacationList);

            // 테이블에서 apvNo와 일치하는 데이터를 삭제합니다.
            apvLineRepository.deleteByApvNo(apvNo);
            apvFileRepository.deleteByApvNo(apvNo);

            System.out.println("savedApvFormMain======================== = " + savedApvFormMain);
            System.out.println("============================================================ 1-2");

            // ApvLine 엔터티 업데이트
            List<ApvLine> apvLineList = apvLineDTOs.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        log.info("apvLine = {}", apvLine);
                        return apvLine;
                    })
                    .collect(Collectors.toList());
            System.out.println("apvLineList ================================== " + apvLineList);
            System.out.println("============================================================ 2");

            // 첨부파일 등록을 위해 서비스로 DTO전달
            List<ApvFile> apvFiles = new ArrayList<>();
            if (apvFileDTO != null && !apvFileDTO.isEmpty()) {
                apvFiles = approvalService.insertFiles(apvNo, apvFileDTO);
            }

            System.out.println("apvFiles ================================== " + apvFiles);
            System.out.println("============================================================ 3");
            // ApvLine, ApvFile 엔티티를 ApvFormMain에 설정
            apvLineRepository.saveAll(apvLineList);
            apvFileRepository.saveAll(apvFiles);
            System.out.println("savedApvForm = " + savedApvForm);
            System.out.println("============================================================ 4");

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


    /* 전자결재 - 인사 : Hrm3 서류발급신청서 */
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

    /* 전자결재 - 인사 : Hrm3 서류발급신청서 수정 */
    @Transactional
    public Boolean updateApvIssuance(Long apvNo, ApvFormDTO apvFormDTO, List<ApvLineDTO> apvLineDTOs, List<MultipartFile> apvFileDTO) {
        log.info("[ApprovalService] Hrm3-updateApvIssuance --------------- 서류발급신청서 업데이트 start");
        log.info("[ApprovalService] apvFormDTO {}", apvFormDTO);
        log.info("[ApprovalService] apvLineDTOs {}", apvLineDTOs);
        log.info("[ApprovalService] apvFileDTO {}", apvFileDTO);
        try {
            // 기존 ApvForm을 검색
            ApvForm savedApvForm = apvFormRepository.findById(apvNo).orElse(null);
            ApvFormMain savedApvFormMain = apvFormMainRepository.findById(apvNo).orElse(null);

            savedApvFormMain.setTitle(apvFormDTO.getTitle());
            savedApvFormMain.setWriteDate(apvFormDTO.getWriteDate());
            savedApvFormMain.setIsUrgency(apvFormDTO.getIsUrgency());
            savedApvFormMain.setCategory(apvFormDTO.getCategory());
            savedApvFormMain.setContents1(apvFormDTO.getContents1());
            savedApvFormMain.setContents2(apvFormDTO.getContents2());

            apvFormMainRepository.save(savedApvFormMain);
            System.out.println("savedApvFormMain======================== = " + savedApvFormMain);
            System.out.println("============================================================ 1");

            apvIssuanceRepository.deleteByApvNo(apvNo);

            List<ApvIssuanceDTO> apvIssuanceDTO = apvFormDTO.getApvIssuances();
            // ApvIssuanceDTO ApvIssuance 엔티티로 매핑하고 ApvNo를 설정
            List<ApvIssuance> apvIssuanceList = apvIssuanceDTO.stream()
                    .map(dto -> {
                        ApvIssuance apvIssuance = modelMapper.map(dto, ApvIssuance.class);
                        apvIssuance.setApvNo(apvNo);
                        return apvIssuance;
                    })
                    .collect(Collectors.toList());

            // ApvIssuance 엔티티를 저장
            apvIssuanceList = apvIssuanceRepository.saveAll(apvIssuanceList);

            // 테이블에서 apvNo와 일치하는 데이터를 삭제합니다.
            apvLineRepository.deleteByApvNo(apvNo);
            apvFileRepository.deleteByApvNo(apvNo);

            System.out.println("savedApvFormMain======================== = " + savedApvFormMain);
            System.out.println("============================================================ 1-2");

            // ApvLine 엔터티 업데이트
            List<ApvLine> apvLineList = apvLineDTOs.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        log.info("apvLine = {}", apvLine);
                        return apvLine;
                    })
                    .collect(Collectors.toList());
            System.out.println("apvLineList ================================== " + apvLineList);
            System.out.println("============================================================ 2");

            // 첨부파일 등록을 위해 서비스로 DTO전달
            List<ApvFile> apvFiles = new ArrayList<>();
            if (apvFileDTO != null && !apvFileDTO.isEmpty()) {
                apvFiles = approvalService.insertFiles(apvNo, apvFileDTO);
            }

            System.out.println("apvFiles ================================== " + apvFiles);
            System.out.println("============================================================ 3");
            // ApvLine, ApvFile 엔티티를 ApvFormMain에 설정
            apvLineRepository.saveAll(apvLineList);
            apvFileRepository.saveAll(apvFiles);
            System.out.println("savedApvForm = " + savedApvForm);
            System.out.println("============================================================ 4");

            // 승인 상태를 확인하고 업데이트
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Hrm3 updateApvIssuance --------------- 서류발급신청서 업데이트 end");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] 오류: Hrm3 updateApvIssuance : " + e.getMessage());
            return false;
        }
    }
}