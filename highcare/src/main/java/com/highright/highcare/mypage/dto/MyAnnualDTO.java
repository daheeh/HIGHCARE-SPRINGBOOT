package com.highright.highcare.mypage.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MyAnnualDTO {


    private int empNo;  // pk ,fk
    private int bAn;
    private int useAn;
    private int addAn;
    private int totalAn;
    //    private String reason;
    private int annNo;  // pk
    private String apvNo;  // fk

    // 필요한 것 -> empNo로 select, 모든 컬럼이 필요

    // 담을 공간 만들기
    private MyApvVationDTO myApvVation;

}
