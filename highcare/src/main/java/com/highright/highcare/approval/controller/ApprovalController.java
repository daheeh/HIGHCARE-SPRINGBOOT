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

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertApv(@RequestBody ApvFormDTO apvFormDTO){

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "상신 등록 성공", approvalService.insertApvForm(apvFormDTO)));
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

    @GetMapping("/write/{empNo}")
    public ResponseEntity<ResponseDTO> selectWriteApv(@PathVariable int empNo){
        List<ApvFormDTO> writeApvList = approvalService.selectWriteApvList(empNo);

            if(writeApvList.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
            }

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),  "작성 기안 조회 성공" , writeApvList));
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


}
