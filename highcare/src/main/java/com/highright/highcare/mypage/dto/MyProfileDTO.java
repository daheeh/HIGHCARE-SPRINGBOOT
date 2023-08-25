package com.highright.highcare.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyProfileDTO {

    private int code;
    private String photo;
    private int empNo;

    private MyEmployeeDTO myEmployeeDTO;

}
