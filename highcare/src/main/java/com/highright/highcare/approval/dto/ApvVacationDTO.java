package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvVacationDTO {

    private String itemNo;
    private String apvNo;
    private Time startDate;
    private Time endDate;
    private String type;
    private String comment;
    private Long amount;

    private ApvFormDTO apvFormDTO;
}
