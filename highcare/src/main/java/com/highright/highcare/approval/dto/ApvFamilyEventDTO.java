package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFamilyEventDTO {

    private String apvNo;
    private String type;
    private Date familyDate;
    private String bank;
    private String accountHolder;
    private String accountNumber;


}
