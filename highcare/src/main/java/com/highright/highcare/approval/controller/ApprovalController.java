package com.highright.highcare.approval.controller;

import com.highright.highcare.approval.dto.ApvFormDTO;
import com.highright.highcare.approval.service.ApprovalService;
import com.highright.highcare.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval")
@Slf4j
public class ApprovalController {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @GetMapping("/write")
    public ResponseEntity<ResponseDTO> selectWriteApvStatusApv(@RequestParam int empNo, @RequestParam String apvStatus){
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

    @GetMapping("/")


    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertApv(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvForm(apvFormDTO)));
    }



    /* 전자결재 - 업무 : biz1 기안서 */
    @PostMapping("/insert/biz1")
    public ResponseEntity<ResponseDTO> insertApvForm(@RequestBody ApvFormDTO apvFormDTO){
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvForm(apvFormDTO)));
    }

    /* 전자결재 - 업무 : biz2 회의록 */
    @PostMapping("/insert/biz2")
    public ResponseEntity<ResponseDTO> insertApvBiz1(@RequestBody ApvFormDTO apvFormDTO){
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvMeetingLog(apvFormDTO)));
    }


    @PostMapping("/insert/exp1")
    public ResponseEntity<ResponseDTO> insertApvExpense(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormDTO)));
    }

    @PostMapping("/insert/exp2")
    public ResponseEntity<ResponseDTO> insertApvExpense2(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvExpense(apvFormDTO)));
    }

    @PostMapping("/insert/exp6")
    public ResponseEntity<ResponseDTO> insertApvFamilyEvent(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvFamilyEvent(apvFormDTO)));
    }

    @PostMapping("/insert/hrm1")
    public ResponseEntity<ResponseDTO> insertApvHrm1(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvVacation(apvFormDTO)));
    }

    @PostMapping("/insert/hrm3")
    public ResponseEntity<ResponseDTO> insertApvHrm3(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvIssuance(apvFormDTO)));
    }



}
