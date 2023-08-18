package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_EXPENSE_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvExpenseDetail {

    @Id
    @Column(name = "ITEMS_NO")
    private String itemsNo;

    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "AMOUNT")
    private Number amount;

    @Column(name = "EXP_COMMENT")
    private String comment;

}
