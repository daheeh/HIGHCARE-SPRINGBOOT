package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_FAMILY_EVENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_ITEMS",
        sequenceName = "SEQ_APV_EV_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvFamilyEvent {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS"
    )
    private Long itemsNo;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "FAMILY_DATE")
    private Date familyDate;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "ACCOUNTHOLDER")
    private String accountHolder;

    @Column(name = "ACCOUNNUMBER")
    private String accountNumber;

    @Column(name = "PAYMENT")
    private int payment;

    @Column(name = "ISSENDINGWREATH")
    private char isSendingWreath;

    @Column(name = "SENDINGNAME")
    private String sendingName;

    @Column(name = "SENDINGADDRESS")
    private String sendingAddress;

    @Column(name = "SENDINGPHONE")
    private String sendingPhone;

    @Column(name = "REQUESTSNOTE")
    private String requestsNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;


}
