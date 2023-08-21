package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApvExpenseDetailDTO {

    private String itemsNo;
    private Long apvNo;
    private String details;
    private String account;
    private int amount;
    private String comment;


}
