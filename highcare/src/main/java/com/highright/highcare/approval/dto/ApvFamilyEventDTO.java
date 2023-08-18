package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvExpenseDetailDTO {

    private String apvNo;
    private Date requestDate;
    private String payee;
    private String bank;
    private String accountHolder;
    private String accountNumber;


}
