package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_APV_ISSUANCE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvIssuance {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SUBMISSION")
    private String submission;

    @Column(name = "USES")
    private String uses;

    @Column(name = "REQUESTS")
    private String requests;

}
