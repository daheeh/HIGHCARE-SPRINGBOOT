package com.highright.highcare.mypage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyApvVationDTO {

    private String ItemNo;    // pk
    private String ApvNo;      // fk로 조인
    private String type;    // 휴가종류 : 반차, 연차
    private Date sdate;
    private Date edate;
    private String comment;
    private String off1;    // 반차
    private String off2;    // 반차끝


}
