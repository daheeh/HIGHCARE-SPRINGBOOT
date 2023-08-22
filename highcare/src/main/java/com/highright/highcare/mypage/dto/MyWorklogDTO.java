package com.highright.highcare.mypage.dto;

import com.highright.highcare.mypage.entity.MyEmployee;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyWorklogDTO {

    private int code;
    private Date wDate;
    private String status;
    private String content;
    private Date sDate;
    private Date eDate;
    private int empNo;

    private MyEmployee myEmp;
}
