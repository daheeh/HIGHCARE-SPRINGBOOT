package com.highright.highcare.auth.controller;

import com.highright.highcare.auth.dto.AUTHFindAccountDTO;
import com.highright.highcare.auth.dto.MailDTO;
import com.highright.highcare.auth.service.FindAccountService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@Slf4j
@RestController
@RequestMapping("/api/auth/find")
@RequiredArgsConstructor
public class FindAccountController {

    private final FindAccountService findAccountService;

    @PostMapping("/mail")
    public ResponseEntity<ResponseDTO> emailSendAuth(@RequestBody MailDTO mailDTO){
        log.info("FindAccountController execMail :==={}", mailDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "이메일 인증 요청", findAccountService.emailSendAuth(mailDTO)));
    }

//    @PostMapping("/phone")
//    public ResponseEntity<ResponseDTO> phoneSendAuth(@ModelAttribute {
//
//    }

    @PostMapping("/authcheck")
    public ResponseEntity<ResponseDTO> selectAuthNumberCheck(@RequestBody AUTHFindAccountDTO authFindAccountDTO){
        log.info("FindAccountController authFindAccountDTO :==={}", authFindAccountDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "인증번호 check", findAccountService.selectAuthNumberCheck(authFindAccountDTO)));
    }

}
