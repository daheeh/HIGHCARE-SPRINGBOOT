package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvVacationDTO {

    private String itemNo;
    private Timestamp startDate;
    private Timestamp endDate;
    private String type;
    private String comment;
    private Long amount;
    private Time offType1;
    private Time offType2;

    private ApvFormDTO apvFormDTO;
}
