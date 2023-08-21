package com.highright.highcare.approval.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApvExpenseDTO {

    private Long apvNo;
    private Date requestDate;
    private String payee;
    private String bank;
    private String accountHolder;
    private String accountNumber;
    private List<ApvExpenseDetailDTO> expenseDetails;


}
