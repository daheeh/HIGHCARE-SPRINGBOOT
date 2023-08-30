package com.highright.highcare.approval.controller;

import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.dto.ApvFormWithLinesDTO;
import com.highright.highcare.approval.dto.ApvLineDTO;
import com.highright.highcare.approval.service.ApprovalService;
import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/approval")
@Slf4j
public class ApprovalController {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }


    /* Apv메인페이지 - 조건별 현황 1 */
    @GetMapping("/writeMain")
    public ResponseEntity<ResponseDTO> selectWriteApv(@RequestParam int empNo){

        Map<String, Integer> countsMap = approvalService.selectWriteApv(empNo);

        System.out.println("countsMap = " + countsMap);

        if(countsMap.isEmpty()){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "작성 기안 상태 조회 성공" , countsMap));
    }

    /* Apv메인페이지 - 리스트 */
    @GetMapping("/apvList")
    public ResponseEntity<ResponseDTO> selectMyApvList(@RequestParam int empNo){

        List<ApvFormDTO> myApvList = approvalService.selectMyApvList(empNo);
        System.out.println("myApvList = " + myApvList);

        if(myApvList.isEmpty()){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "작성 기안 상태 조회 성공" , myApvList));
    }



    /* 전자결재 결재함 조회 */
    @GetMapping("/write")
    public ResponseEntity<ResponseDTO> selectWriteApvStatusApv(@RequestParam int empNo, @RequestParam String apvStatus
                                                                ,@RequestParam(name = "offset", defaultValue = "1") String offset){

        log.info("[ProductController] selectProductListWithPaging => offset : {} ", offset);

        int total = approvalService.selectWriteApvStatusTotal(empNo,apvStatus);
        Criteria criteria = new Criteria(Integer.valueOf(offset), 15);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(approvalService.selectProductListWithPaging(empNo,apvStatus, criteria));
        pagingResponseDTO.setPageInfo(new PageDTO(criteria, total));

        List<ApvFormDTO> writeApvStatusApvList = approvalService.selectWriteApvStatusApvList(empNo, apvStatus);

        if(writeApvStatusApvList.isEmpty()){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
        }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "작성 기안 상태 조회 성공" , writeApvStatusApvList));
    }

    /* 전자결재 - 업무 : biz1 기안서 */
    @PostMapping("/insert/biz1")
    public ResponseEntity<ResponseDTO> insertApvFormWithLines(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
        System.out.println("biz1 apvFormWithLinesDTO = " + apvFormWithLinesDTO);

        Object serviceResponse =  approvalService.insertApvFormWithLines(apvFormWithLinesDTO);
        int statusCode;
        String responseMessage;

            if (serviceResponse.equals("기안 상신 실패")) {
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
    @PostMapping("/insert/biz2")
    public ResponseEntity<ResponseDTO> insertApvMeetingLog(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO) {
        System.out.println("biz2 apvFormWithLinesDTO = " + apvFormWithLinesDTO);

        Boolean serviceResponse = approvalService.insertApvMeetingLog(apvFormWithLinesDTO);
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

//    @PostMapping("/insert/biz2")
//    public ResponseEntity<ResponseDTO> insertApvMeetingLog(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//        System.out.println("biz2 apvFormWithLinesDTO = " + apvFormWithLinesDTO);
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvMeetingLog(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 업무 : biz3 출장신청서 */
//    @PostMapping("/insert/biz3")
//    public ResponseEntity<ResponseDTO> insertApvBusinessTrip(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvBusinessTrip(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 지출 : exp1 지출결의서 */
//    @PostMapping("/insert/exp1")
//    public ResponseEntity<ResponseDTO> insertApvExpense(@RequestBody ApvFormDTO apvFormDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 지출 : exp2 지출결의서 */
//    @PostMapping("/insert/exp2")
//    public ResponseEntity<ResponseDTO> insertApvExpense2(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 지출 : exp4 출장경비정산서 */
//    @GetMapping("/search/exp4")
//    public ResponseEntity<ResponseDTO> selectApvBusinessTripExp(@RequestParam int empNo, @RequestParam String title){
//        List<ApvFormDTO> apvBusinessTripList = approvalService.selectApvBusinessTripExp(empNo, title);
//
//        if(apvBusinessTripList.isEmpty()){
//            return ResponseEntity
//                    .ok()
//                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
//        }
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(),  "작성 기안 상태 조회 성공" , apvBusinessTripList));
//    }
//    @PostMapping("/insert/exp4")
//    public ResponseEntity<ResponseDTO> insertApvBusinessTripExp(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 지출 : exp6 경조금 신청서 */
//    @PostMapping("/insert/exp6")
//    public ResponseEntity<ResponseDTO> insertApvFamilyEvent(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvFamilyEvent(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 지출 : exp7 법인카드사용보고서 */
//    @PostMapping("/insert/exp7")
//    public ResponseEntity<ResponseDTO> insertApvCorpCard(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvCorpCard(apvFormWithLinesDTO)));
//    }
//
//
//    /* 전자결재 - 인사 : hrm1 연차신청서, hrm2 기타휴가신청서 */
//    @PostMapping("/insert/hrm1")
//    public ResponseEntity<ResponseDTO> insertApvVacation(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvVacation(apvFormWithLinesDTO)));
//    }
//
//    /* 전자결재 - 인사 : hrm3 서류발급신청서 */
//    @PostMapping("/insert/hrm3")
//    public ResponseEntity<ResponseDTO> insertApvIssuance(@RequestBody ApvFormWithLinesDTO apvFormWithLinesDTO){
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvIssuance(apvFormWithLinesDTO)));
//    }
//


}
