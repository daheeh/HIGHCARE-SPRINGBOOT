package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvBusinessTripDTO {

    private String apvNo;
    private String purpose;
    private Date startDate;
    private Date endDate;
    private String location;
}
