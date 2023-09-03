package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvOvertimeDTO {

    private Long itemNo;
    private Date workingDate;
    private Time startTime;
    private Time endTime;
    private String reason;
    private Long apvNo;

//    private ApvFormDTO apvFormDTO;
}
