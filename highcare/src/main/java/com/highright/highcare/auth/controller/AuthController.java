package com.highright.highcare.auth.controller;


import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.auth.service.AuthService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> Login(@RequestBody LoginMemberDTO loginMemberDTO){
        log.info("[AuthController] Login : loginMemberDTO ==== {}", loginMemberDTO);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "로그인 성공", authService.login(loginMemberDTO)));
    }


}
