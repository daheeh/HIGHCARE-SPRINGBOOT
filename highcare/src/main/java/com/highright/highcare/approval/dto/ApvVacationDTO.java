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

    private String apvNo;
    private Date startDate;
    private Date endDate;
    private String type;
    private String comment;

}
