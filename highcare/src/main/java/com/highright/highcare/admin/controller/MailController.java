package com.highright.highcare.admin.controller;

import com.highright.highcare.admin.dto.MailDTO;
import com.highright.highcare.admin.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public void execMail(@RequestBody MailDTO mailDTO){
        log.info("MailController execMail :==={}", mailDTO);

        mailService.mailSend(mailDTO);
    }
}
