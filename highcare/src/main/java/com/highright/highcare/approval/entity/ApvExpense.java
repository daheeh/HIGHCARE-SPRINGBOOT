package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
    private Long apvNo;


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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;

    @OneToMany(mappedBy = "apvExpense", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ApvExpenseDetail> expenseDetails = new ArrayList<>();

    public ApvExpense(ApvForm apvForm) {
        this.apvForm = apvForm;
        this.apvNo = apvForm.getApvNo();
//        apvForm.setApvExpense(this);
    }
}
