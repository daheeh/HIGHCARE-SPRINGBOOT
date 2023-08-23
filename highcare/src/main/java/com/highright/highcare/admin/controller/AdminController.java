package com.highright.highcare.admin.controller;


import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.admin.service.AdminService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("main")
    public ResponseEntity<ResponseDTO> selectAdmin(LoginMemberDTO loginMemberDTO
            , HttpServletResponse response){
        log.info("[AdminController] Admin : Admin ==== {}", "관리자페이지 접속 성공");


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "관리자 페이지 접속 성공", null));
    }

    @GetMapping("member")
    public ResponseEntity<ResponseDTO> selectMember(@RequestParam int empNo){

        log.info("memberNo" , empNo);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "사원 조회 성공", adminService.selectMember(empNo)));
    }

    // 인서트 회원신청





}
