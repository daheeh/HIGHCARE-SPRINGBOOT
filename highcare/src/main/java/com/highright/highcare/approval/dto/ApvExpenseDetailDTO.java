package com.highright.highcare.approval.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvExpenseDTO {

    private String apvNo;
    private Date requestDate;
    private String payee;
    private String bank;
    private String accountHolder;
    private String accountNumber;


}
