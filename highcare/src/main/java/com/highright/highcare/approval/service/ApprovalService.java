package com.highright.highcare.approval.service;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.*;

import com.highright.highcare.approval.repository.*;
import com.highright.highcare.common.Criteria;
import com.highright.highcare.util.FileUploadUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.fenum.qual.SwingTitleJustification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApprovalService {

    private final ModelMapper modelMapper;
    private final ApvFormMainRepository apvFormMainRepository;
    private final ApvFormRepository apvFormRepository;
    private final ApvLineRepository apvLineRepository;
    private final ApvFileRepository apvFileRepository;
    private final ApvMeetingLogRepository apvMeetingLogRepository;
    private final ApvBusinessTripRepository apvBusinessTripRepository;
    private final ApvOfficialRepository apvOfficialRepository;
    private final ApvExpFormRepository apvExpFormRepository;
    private final ApvFamilyEventRepository apvFamilyEventRepository;
    private final ApvCorpCardRepository apvCorpCardRepository;
    private final ApvVacationRepository apvVacationRepository;
    private final ApvIssuanceRepository apvIssuanceRepository;




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
    public List<String> selectMyApvList(int empNo) {
        log.info("[ApprovalService] selectMyApvList --------------- start ");

        List<String> writeApvList = apvFormRepository.findTitlesByEmpNo(empNo);

        List<String> titleList = Arrays.asList(
                "회의록",
                "출장신청서",
                "공문",
                "지출결의서",
                "출장경비정산서",
                "경조금신청서",
                "법인카드사용보고서",
                "연차신청서",
                "기타휴가신청서",
                "서류발급신청서"
        );

        writeApvList = writeApvList.stream()
                .map(item -> titleList.contains(item) ? item : "기안서")
                .collect(Collectors.toList());
        log.info("[ApprovalService] selectMyApvList --------------- end ");

        return writeApvList;
    }


    /* 전자결재 결재함 조회 */
    public List<ApvFormMainDTO> selectWriteApvStatusApvList(int empNo, String apvStatus) {
        log.info("[ApprovalService] selectWriteApvStatusApvList --------------- start ");

        System.out.println("empNo = " + empNo);
        List<ApvFormMain> writeApvList = apvFormMainRepository.findByEmpNoAndApvStatusOrderByApvNoDesc(empNo, apvStatus);

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

        List<ApvFormMain> apvList = apvFormMainRepository.findByEmpNoAndApvStatusOrderByApvNoDesc(empNo, apvStatus);

        log.info("[ApprovalService] selectApvStatusTotal --------------- end ");

        return apvList.size();
    }

    public Object selectListWithPaging(int empNo, String apvStatus, Criteria criteria) {
        log.info("[ApprovalService] selectListWithPaging => Start =============");

        int index = criteria.getPageNum() - 1;
        int count = criteria.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("apvNo").descending());

        Page<ApvFormMain> result1 = apvFormMainRepository.findByEmpNoAndApvStatusOrderByApvNoDesc(empNo, apvStatus, paging);
        List<ApvFormMain> writeApvList = (List<ApvFormMain>) result1.getContent();
        writeApvList.forEach(ApvFormMain::getEmployee);

        log.info("[ApprovalService] selectListWithPaging => end =============");
        return writeApvList.stream().map(apvFormMain -> modelMapper.map(apvFormMain, ApvFormMainDTO.class)).collect(Collectors.toList());
    }


    // 결재 승인
    @Transactional
    public boolean updateApprovalStatus(Long apvLineNo, Long apvNo) {
        log.info("[ApprovalService] updateApprovalStatus --------------- start ");
        System.out.println("updateIsApproval???????????????==============");

        try {

            apvLineRepository.updateIsApproval(apvLineNo);
            System.out.println("updateIsApproval==============");
            int approved = apvLineRepository.areAllApproved(apvLineNo);
            System.out.println("approved = " + approved);
            if (approved == 0) {
                apvFormRepository.updateApvStatusToCompleted(apvNo);
                System.out.println("11111111111111==============");
            } else if (approved >= 0) {
                apvFormRepository.updateApvStatusToProcess(apvNo);
                System.out.println("222222222222222==============");
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
            apvFileRepository.deleteByApvNo(apvNo);
            apvMeetingLogRepository.deleteByApvNo(apvNo);
            apvBusinessTripRepository.deleteByApvNo(apvNo);
            apvMeetingLogRepository.deleteByApvNo(apvNo);
            apvOfficialRepository.deleteByApvNo(apvNo);
            apvExpFormRepository.deleteByApvNo(apvNo);
            apvFamilyEventRepository.deleteByApvNo(apvNo);
            apvCorpCardRepository.deleteByApvNo(apvNo);
            apvVacationRepository.deleteByApvNo(apvNo);
            apvIssuanceRepository.deleteByApvNo(apvNo);
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
        log.info("[ApprovalService] searchApvFormWithLines --------------- start ");

        ApvForm apvForm = apvFormRepository.findByApvNo(apvNo);
        String title = apvFormRepository.findTitleByApvNo(apvNo);

        if(title.equals("출장경비정산서")){
            apvForm.setApvBusinessTrips(apvBusinessTripRepository.findByApvNo(apvForm.getRefApvNo()));
        }

        apvForm.getApvLines().forEach(ApvLine::getEmployee);
        apvForm.getEmployee();

        if (apvForm == null) {
            log.error("[ApprovalService] Error: ApvForm not found with apvNo {}", apvNo);
            return null;
        }

        ApvFormDTO apvFormDTO = modelMapper.map(apvForm, ApvFormDTO.class);
        System.out.println("apvFormDTO = " + apvFormDTO);
        log.info("[ApprovalService] searchApvFormWithLines --------------- end ");
        return apvFormDTO;
    }


    /* 파일 저장 할 위치 및 응답 할 주소 */
    @Value("${file.upload-dir}")
    private String FILE_DIR;
    @Value("${file.base-url}")
    private String FILE_URL;


    // 파일 첨부
    @Transactional
    public List<ApvFile> insertFiles(Long apvNo, List<MultipartFile> apvFileDTO) {
        System.out.println("insertFiles =============================== ");
        System.out.println("apvFileDTO = " + apvFileDTO);

        List<ApvFile> apvFiles = new ArrayList<>();

        // 디렉토리가 존재하지 않으면 생성합니다.
        File directory = new File(FILE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (MultipartFile multipartFile : apvFileDTO) {
            try {
                String fileName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = null;
                // 파일 저장
                replaceFileName = FileUploadUtils.saveFile(FILE_DIR, fileName, multipartFile);
                String saveFileUrl = FileUploadUtils.saveFile(FILE_DIR, fileName, multipartFile);
                String originalFileName = multipartFile.getOriginalFilename();

                // ApvFile 엔티티 생성 및 저장
                ApvFile apvFile = new ApvFile();
                apvFile.setApvNo(apvNo);
                apvFile.setSavedFileName(replaceFileName);
                apvFile.setOriginalFileName(originalFileName);

                apvFiles.add(apvFile); // 리스트에 추가

            } catch (Exception e) {
                // 예외 처리
//                throw new RuntimeException(e);
                e.printStackTrace(); // 파일 등록 실패하면 기안 실패하는 걸로 수정해야함
            }

        }
        return apvFiles;
    }

    // 첨부파일 수정

    @Transactional
    public List<ApvFile> updateFiles(Long apvNo, List<MultipartFile> apvFileDTO) {
        System.out.println("updateFiles =============================== ");
        System.out.println("apvFileDTO = " + apvFileDTO);

        List<ApvFile> apvFiles = new ArrayList<>();

        // 디렉토리가 존재하지 않으면 생성합니다.
        File directory = new File(FILE_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 기존 파일 삭제 (apvNo에 연결된 파일 삭제)
        apvFileRepository.deleteByApvNo(apvNo);

        for (MultipartFile multipartFile : apvFileDTO) {
            try {
                String fileName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = null;
                // 파일 저장
                replaceFileName = FileUploadUtils.saveFile(FILE_DIR, fileName, multipartFile);
                String saveFileUrl = FileUploadUtils.saveFile(FILE_DIR, fileName, multipartFile);
                String originalFileName = multipartFile.getOriginalFilename();

                // ApvFile 엔티티 생성 및 저장
                ApvFile apvFile = new ApvFile();
                apvFile.setApvNo(apvNo);
                apvFile.setSavedFileName(replaceFileName);
                apvFile.setOriginalFileName(originalFileName);

                apvFiles.add(apvFile); // 리스트에 추가

            } catch (Exception e) {
                // 예외 처리
//            throw new RuntimeException(e);
                e.printStackTrace(); // 파일 등록 실패하면 업데이트 실패하는 걸로 수정해야함
            }
        }
        return apvFiles;
    }


}

