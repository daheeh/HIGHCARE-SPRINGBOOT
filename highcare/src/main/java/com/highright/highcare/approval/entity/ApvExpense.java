package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_EXPENSE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvExpense {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "REQUEST_DATE")
    private Date requestDate;

    @Column(name = "PAYEE")
    private String payee;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "ACCOUNTHOLDER")
    private String accountHolder;

    @Column(name = "ACCOUNNUMBER")
    private String accountNumber;


}
