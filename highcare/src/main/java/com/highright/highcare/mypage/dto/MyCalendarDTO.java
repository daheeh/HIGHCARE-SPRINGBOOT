package com.highright.highcare.mypage.dto;

import com.highright.highcare.mypage.entity.MyEmployee;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MyCalendarDTO {

    private int code;
    private String contents;
    private Date date;
    private String startTime;
    private String endTime;
    private int empNo;

    private MyEmployee myEmp;

}
