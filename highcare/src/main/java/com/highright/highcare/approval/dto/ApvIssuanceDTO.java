package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvIssuanceDTO {

    private String apvNo;
    private String type;
    private String submission;
    private String uses;
    private String requests;

}
