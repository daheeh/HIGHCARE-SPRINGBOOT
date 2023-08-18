package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_FAMILY_EVENT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFamilyEvent {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

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


}
