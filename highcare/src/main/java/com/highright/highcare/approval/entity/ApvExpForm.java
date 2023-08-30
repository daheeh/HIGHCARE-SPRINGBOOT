package com.highright.highcare.approval.entity;

import com.highright.highcare.approval.entity.ApvForm;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
@Entity
@Table(name = "TBL_APV_EXPFORM")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_ITEMS03",
        sequenceName = "SEQ_APV_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvExpForm {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS03"
    )
    private Long itemsNo;

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

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "EXP_COMMENT")
    private String comment;

    @Column(name = "APV_NO")
    private Long apvNo;
}
