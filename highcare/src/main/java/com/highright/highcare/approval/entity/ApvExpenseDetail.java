package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_EXPENSE_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SequenceGenerator(
        name = "SEQ_APV_ITEMS",
        sequenceName = "SEQ_APV_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvExpenseDetail {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS"
    )
    private Long itemsNo;

    @ManyToOne
    @JoinColumn(name = "APV_NO")
    private ApvExpense apvExpense;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "EXP_COMMENT")
    private String comment;

}
