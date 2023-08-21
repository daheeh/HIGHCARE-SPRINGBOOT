package com.highright.highcare.admin.controller;


import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.auth.service.AuthService;
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

    private final AuthService authService;

    @GetMapping("main")
    public String Admin( LoginMemberDTO loginMemberDTO
                                            , HttpServletResponse response){
        log.info("[AdminController] Admin : Admin ==== {}", "관리자페이지 접속 성공");


        return "관리자페이지 접속 성공";
    }





}
