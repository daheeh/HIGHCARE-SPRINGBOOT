package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvExpenseDetailDTO {

    private String itemsNo;
    private String apvNo;
    private String details;
    private String account;
    private Number amount;
    private String comment;


}
