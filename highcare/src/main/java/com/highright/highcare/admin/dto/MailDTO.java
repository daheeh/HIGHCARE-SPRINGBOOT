package com.highright.highcare.admin.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MailDTO {
    private String address;
    private String title;
    private String message;
    private MultipartFile file;
}