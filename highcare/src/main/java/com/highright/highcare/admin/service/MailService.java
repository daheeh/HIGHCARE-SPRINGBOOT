package com.highright.highcare.admin.service;

import com.highright.highcare.admin.dto.MailDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "highcaretest@high.or.kr";

    public void mailSend(MailDTO mailDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());
        log.info("MailService message ==={}  : ", message);
        javaMailSender.send(message);
    }
}
