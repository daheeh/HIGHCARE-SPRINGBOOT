package com.highright.highcare.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyProfileDTO {

    private int code;
    private String phone;
    private String email;
    private String photo;
    private String chPhoto;
    private String adress;
    private int fileCode;

    private int empNo;

}
