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

    private String apvNo;
    private Date workingDate;
    private Time startTime;
    private Time endTime;
    private String reason;

}
