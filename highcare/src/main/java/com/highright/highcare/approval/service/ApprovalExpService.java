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
            // ApvFormWithLinesDTO에서 필요한 데이터 추출
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvExpFormDTO> apvExpFormDTOList = apvFormDTO.getApvExpForms();
            List<ApvLineDTO> apvLineDTOList = apvFormWithLinesDTO.getApvLineDTOs();

            // ApvForm 및 ApvFormMain 생성 및 저장
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvExpFormDTO를 ApvExpForm 엔티티로 매핑하고 ApvNo 설정
            List<ApvExpForm> apvExpFormList = apvExpFormDTOList.stream()
                    .map(apvExpFormDTO -> {
                        ApvExpForm apvExpForm = modelMapper.map(apvExpFormDTO, ApvExpForm.class);
                        apvExpForm.setApvNo(apvNo);
//                        apvExpForm.getApvForm().setApvNo(apvNo);
                        return apvExpForm;
                    })
                    .collect(Collectors.toList());

            // ApvExpForm 엔티티 저장
            apvExpFormList = apvExpFormRepository.saveAll(apvExpFormList);

            // ApvLineDTO를 ApvLine 엔티티로 매핑하고 ApvNo 설정
            List<ApvLine> apvLineList = apvLineDTOList.stream()
                    .map(apvLineDTO -> {
                        ApvLine apvLine = modelMapper.map(apvLineDTO, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // ApvFormMain에 ApvLines 설정
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태 확인 후 결재 상태 변경
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Exp1 insertApvExpense --------------- 종료 ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] 오류 발생 - Exp1 insertApvExpense : " + e.getMessage());
            return false;
        }
    }


    /* 전자결재 - 지출 : exp6 경조금 신청서 */
    @Transactional
    public Boolean insertApvFamilyEvent(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] Exp6 insertApvFamilyEvent --------------- start ");
        log.info("[ApprovalHrmService] Exp6 apvFormWithLinesDTO {}", apvFormWithLinesDTO);

        try {
            // ApvFormWithLinesDTO에서 필요한 데이터 추출
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvFamilyEventDTO> apvFamilyEventDTO = apvFormDTO.getApvFamilyEvents();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            // ApvFormMain을 저장하고 생성된 ApvNo를 가져오기
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvFamilyEventDTO를 ApvFamilyEvent 엔티티로 매핑하고 ApvNo를 설정
            List<ApvFamilyEvent> apvFamilyEventList = apvFamilyEventDTO.stream()
                    .map(dto -> {
                        ApvFamilyEvent apvFamilyEvent = modelMapper.map(dto, ApvFamilyEvent.class);
                        apvFamilyEvent.setApvNo(apvNo);
//                        apvFamilyEvent.getApvForm().setApvNo(apvNo);
                        return apvFamilyEvent;
                    })
                    .collect(Collectors.toList());

            // ApvFamilyEvent 엔티티를 저장
            apvFamilyEventList = apvFamilyEventRepository.saveAll(apvFamilyEventList);

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

            log.info("[ApprovalService] Exp6 insertApvFamilyEvent --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Exp6 insertApvFamilyEvent : " + e.getMessage());
            return false;
        }
    }


    /* 전자결재 - 지출 : exp7 법인카드사용보고서 */
    @Transactional
    public Boolean insertApvCorpCard(ApvFormWithLinesDTO apvFormWithLinesDTO) {
        log.info("[ApprovalService] insertApvCorpCard --------------- start ");
        log.info("[ApprovalHrmService] Exp7 insertApvCorpCard {}", apvFormWithLinesDTO);

        try {
            // ApvFormWithLinesDTO에서 필요한 데이터 추출
            ApvFormDTO apvFormDTO = apvFormWithLinesDTO.getApvFormDTO();
            List<ApvCorpCardDTO> apvCorpCardDTO = apvFormDTO.getApvCorpCards();
            List<ApvLineDTO> apvLineDTO = apvFormWithLinesDTO.getApvLineDTOs();

            // ApvFormMain과 ApvForm 생성 및 저장
            ApvForm apvForm = modelMapper.map(apvFormDTO, ApvForm.class);
            ApvFormMain apvFormMain = modelMapper.map(apvFormDTO, ApvFormMain.class);
            apvFormMain = apvFormMainRepository.save(apvFormMain);
            Long apvNo = apvFormMain.getApvNo();

            // ApvCorpCardDTO를 ApvCorpCard 엔티티로 매핑하고 ApvNo 설정
            List<ApvCorpCard> apvCorpCardList = apvCorpCardDTO.stream()
                    .map(dto -> {
                        ApvCorpCard apvCorpCard = modelMapper.map(dto, ApvCorpCard.class);
                        apvCorpCard.setApvNo(apvNo);
//                    apvCorpCard.getApvForm().setApvNo(apvNo);
                        return apvCorpCard;
                    })
                    .collect(Collectors.toList());

            // ApvCorpCard 엔티티를 저장
            apvCorpCardList = apvCorpCardRepository.saveAll(apvCorpCardList);

            // ApvLineDTO를 ApvLine 엔티티로 매핑하고 ApvNo 설정
            List<ApvLine> apvLineList = apvLineDTO.stream()
                    .map(dto -> {
                        ApvLine apvLine = modelMapper.map(dto, ApvLine.class);
                        apvLine.setApvNo(apvNo);
                        return apvLine;
                    })
                    .collect(Collectors.toList());

            // ApvFormMain에 ApvLines 설정
            apvFormMain.setApvLines(apvLineList);

            // 승인 상태 확인 후 결재 상태 변경
            if (apvLineRepository.apvNoAllApproved(apvNo) == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
            }

            log.info("[ApprovalService] Exp7 insertApvCorpCard --------------- end ");
            return true;
        } catch (Exception e) {
            log.error("[ApprovalService] Error Exp7 insertApvCorpCard : " + e.getMessage());
            return false;
        }
    }

}