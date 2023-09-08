package com.highright.highcare.approval.controller;

import com.highright.highcare.approval.dto.*;
import com.highright.highcare.approval.entity.ApvForm;
import com.highright.highcare.approval.repository.ApvFormRepository;
import com.highright.highcare.approval.service.ApprovalBizService;
import com.highright.highcare.approval.service.ApprovalExpService;
import com.highright.highcare.approval.service.ApprovalHrmService;
import com.highright.highcare.approval.service.ApprovalService;
import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/approval")
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
@RequiredArgsConstructor
public class ApprovalController {

    private final ApprovalService approvalService;
    private final ApprovalBizService approvalBizService;
    private final ApprovalExpService approvalExpService;
    private final ApprovalHrmService approvalHrmService;
    private final ApvFormRepository apvFormRepository;


    /* Apv메인페이지 - 조건별 현황 1 */
    @GetMapping("/main")
    public ResponseEntity<ResponseDTO> selectWriteApv(@RequestParam int empNo) {

        Map<String, Integer> countsMap = approvalService.selectApvMainCount(empNo);

        System.out.println("countsMap = " + countsMap);

        if (countsMap.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "작성 기안 상태 조회 성공", countsMap));
    }

    /* Apv메인페이지 - 리스트 */
    @GetMapping("/apvList")
    public ResponseEntity<ResponseDTO> selectMyApvList(@RequestParam int empNo) {

//        List<ApvFormMainDTO> myApvList = approvalService.selectMyApvList(empNo);
        List<String> myApvList = approvalService.selectMyApvList(empNo);
        System.out.println("myApvList = " + myApvList);

        if (myApvList.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "작성 기안 상태 조회 성공", myApvList));
    }


    /* 전자결재 결재함 조회 */
    @GetMapping("/write")
    public ResponseEntity<ResponseDTO> selectWriteApvStatusApv(@RequestParam int empNo, @RequestParam String apvStatus
            , @RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[ProductController] selectListWithPaging => offset : {} ", offset);

        int total = approvalService.selectApvStatusTotal(empNo, apvStatus);
        Criteria criteria = new Criteria(Integer.valueOf(offset), 15);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(approvalService.selectListWithPaging(empNo, apvStatus, criteria));
        pagingResponseDTO.setPageInfo(new PageDTO(criteria, total));

        List<ApvFormMainDTO> writeApvStatusApvList = approvalService.selectWriteApvStatusApvList(empNo, apvStatus);

        if (writeApvStatusApvList.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "작성 기안 상태 조회 성공", writeApvStatusApvList));
    }

    /* 전자결재 수신함 조회 */
    @GetMapping("/receive")
    public ResponseEntity<ResponseDTO> selectReceiveApvStatusApv(@RequestParam int empNo, @RequestParam String apvStatus
            , @RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("[ProductController] selectReceiveApvStatusApv => offset : {} ", offset);

        int total = approvalService.selectApvStatusTotal(empNo, apvStatus);
        Criteria criteria = new Criteria(Integer.valueOf(offset), 15);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(approvalService.selectListWithPaging(empNo, apvStatus, criteria));
        pagingResponseDTO.setPageInfo(new PageDTO(criteria, total));

        List<ApvFormMainDTO> receiveApvStatusApvList = approvalService.selectReceiveApvStatusApvList(empNo, apvStatus);

        if (receiveApvStatusApvList.isEmpty()) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "작성 기안 상태 조회 성공", receiveApvStatusApvList));
    }


    int statusCode;
    String responseMessage;


    /* 전자결재 승인 + 반려*/
    @PutMapping("/put/line/{apvLineNo}")
    public ResponseEntity<ResponseDTO> updateApprovalStatus(@PathVariable Long apvLineNo, @RequestParam Long apvNo, @RequestParam String apvStatus) {

        System.out.println("apvLineNo = " + apvLineNo);
        System.out.println("apvNo = " + apvNo);
        System.out.println("apvStatus = " + apvStatus);
        boolean updatedResponse = false;

        if(apvStatus == null || apvStatus.isEmpty()) {
            updatedResponse = approvalService.updateApprovalStatus(apvLineNo, apvNo);
        } else {
            updatedResponse = approvalService.updateApvStatusReject(apvNo);
        }


        if (!updatedResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, updatedResponse));

    }

    // 기안 취소(삭제)
    @DeleteMapping("/delete/{apvNo}")
    public ResponseEntity<ResponseDTO> deleteApvForm(@PathVariable Long apvNo) {

        Boolean serviceResponse = approvalService.deleteApvForm(apvNo);

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    // 기안 조회
    @GetMapping("/search/{apvNo}")
    public ResponseEntity<?> searchApvFormWithLines(@PathVariable Long apvNo) {
        System.out.println("biz1View searchApvFormWithLines = " + apvNo);

        ApvFormDTO serviceResponse = approvalService.searchApvFormWithLines(apvNo);

        if (serviceResponse == null) {
            statusCode = HttpStatus.NOT_FOUND.value();
            responseMessage = "ApvForm not found with apvNo: " + apvNo;
            return ResponseEntity
                    .status(statusCode)
                    .body(new ResponseDTO(statusCode, responseMessage, null));
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "조회 성공";
            return ResponseEntity
                    .status(statusCode)
                    .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
        }
    }

    // 기안 수정
//    @PutMapping(value = "/put/{apvNo}", consumes = "multipart/form-data")
//    public ResponseEntity<ResponseDTO> putApvFormWithLines(
//            @PathVariable Long apvNo,
//            @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
//            @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
//            @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
//        System.out.println("apvFormDTO = " + apvFormDTO);
//        System.out.println("apvLineDTOs = " + apvLineDTOs);
//        System.out.println("apvFileDTO = " + apvFileDTO);
//
//        // 서비스 메서드를 호출하여 ApvFormWithLinesDTO 및 파일 정보를 등록
//        Boolean serviceResponse = approvalService.updateApvForm(apvNo, apvFormDTO, apvLineDTOs, apvFileDTO);
//
//        if (!serviceResponse) {
//            statusCode = HttpStatus.BAD_REQUEST.value();
//            responseMessage = "상신 등록 실패";
//        } else {
//            statusCode = HttpStatus.OK.value();
//            responseMessage = "상신 등록 성공";
//        }
//        return ResponseEntity
//                .status(statusCode)
//                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
//    }



    /* 전자결재 - 업무 : biz1 기안서 */
    @PostMapping(value = "/insert/biz1")
    public ResponseEntity<ResponseDTO> insertApvFormWithLines(
                                                            @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                            @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                            List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        // 서비스 메서드를 호출하여 ApvFormWithLinesDTO 및 파일 정보를 등록
        Boolean serviceResponse = approvalBizService.insertApvFormWithLines(apvFormDTO, apvLineDTOs, apvFileDTO);

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    /* 전자결재 - 업무 : biz2 회의록 */
    @PostMapping(value = "/insert/biz2", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvMeetingLog(
                                                            @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                            @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                            @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);


        Boolean serviceResponse = approvalBizService.insertApvMeetingLog(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    /* 전자결재 - 업무 : biz3 출장신청서 */
    @PostMapping(value ="/insert/biz3", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvBusinessTrip(
                                                            @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                            @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                            @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalBizService.insertApvBusinessTrip(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }



    //    /* 전자결재 - 지출 : exp1 지출결의서 */
    @PostMapping(value ="/insert/exp1", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvExpense(
                                                        @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                        @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                        @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("exp1 apvFormDTO = " + apvFormDTO);
        System.out.println("exp1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("exp1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalExpService.insertApvExpense(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    /* 전자결재 - 지출 : exp2 지출결의서 */
//    @PostMapping(value ="/insert/exp2", consumes = "multipart/form-data")
//    public ResponseEntity<ResponseDTO> insertApvExpense2(
//                                                        @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
//                                                        @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
//                                                        @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
//        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
//        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
//        System.out.println("biz1 apvFileDTO = " + apvFileDTO);
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormDTO, apvLineDTOs, apvFileDTO)));
//    }



    /* 전자결재 - 지출 : exp4 출장경비정산서 */
    @PostMapping(value ="/search/exp4/{empNo}", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> selectApvBusinessTrip(@PathVariable int empNo) {
        System.out.println("exp4-searchApvFormWithLines = " + empNo);

        List<ApvBusinessTripDTO> serviceResponse = approvalBizService.selectApvBusinessTrip(empNo);

        if (serviceResponse == null || serviceResponse.isEmpty()) {
            statusCode = HttpStatus.NOT_FOUND.value();
            responseMessage = "ApvForm not found with empNo: " + empNo;
            return ResponseEntity
                    .status(statusCode)
                    .body(new ResponseDTO(statusCode, responseMessage, null));
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "조회 성공";
            return ResponseEntity
                    .status(statusCode)
                    .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
        }
    }


    @PostMapping(value ="/insert/exp4", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvBusinessTripExp(
                                                                @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                                @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                                @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalExpService.insertApvExpense(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    /* 전자결재 - 지출 : exp6 경조금 신청서 */
    @PostMapping(value="/insert/exp6", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvFamilyEvent(
                                                            @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                            @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                            @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalExpService.insertApvFamilyEvent(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }

    /* 전자결재 - 지출 : exp7 법인카드사용보고서 */
    @PostMapping(value ="/insert/exp7", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvCorpCard(
                                                        @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                        @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                        @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalExpService.insertApvCorpCard(apvFormDTO, apvLineDTOs, apvFileDTO);
        int statusCode;
        String responseMessage;

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }


    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
    @PostMapping(value = "/insert/hrm1", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvVacation(
                                                        @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                        @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                        @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        // 서비스 메서드를 호출하여  ApvFormDTO, ApvLineDTOs, 파일 정보를 등록
        Boolean serviceResponse = approvalHrmService.insertApvVacation(apvFormDTO, apvLineDTOs, apvFileDTO);

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }



    /* 전자결재 - 인사 : hrm3 서류발급신청서 */
    @PostMapping(value ="/insert/hrm3", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO> insertApvIssuance(
                                                        @RequestPart("apvFormDTO") ApvFormDTO apvFormDTO,
                                                        @RequestPart("apvLineDTOs") List<ApvLineDTO> apvLineDTOs,
                                                        @RequestPart(value = "apvFileDTO", required = false) List<MultipartFile> apvFileDTO) {
        System.out.println("biz1 apvFormDTO = " + apvFormDTO);
        System.out.println("biz1 apvLineDTOs = " + apvLineDTOs);
        System.out.println("biz1 apvFileDTO = " + apvFileDTO);

        Boolean serviceResponse = approvalHrmService.insertApvIssuance(apvFormDTO, apvLineDTOs, apvFileDTO);

        if (!serviceResponse) {
            statusCode = HttpStatus.BAD_REQUEST.value();
            responseMessage = "상신 등록 실패";
        } else {
            statusCode = HttpStatus.OK.value();
            responseMessage = "상신 등록 성공";
        }
        return ResponseEntity
                .status(statusCode)
                .body(new ResponseDTO(statusCode, responseMessage, serviceResponse));
    }

}