package com.highright.highcare.mypage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class MyManegementDTO {

    private int manNo;
    private String sTime;
    private String eTime;
    private String manTime;
    private String status;
    private int empNo;
    private int workDate;
    private int tWorkDate;
}
