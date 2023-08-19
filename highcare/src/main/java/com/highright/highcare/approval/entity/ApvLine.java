package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_APV_LINE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvLine {

    @Id
    @Column(name = "APV_LINE_NO")
    private String apvLineNo;

    @Column(name = "DEGREE")
    private String degree;

    @Column(name = "ISAPPROVAL")
    private char isApproval;

    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "EMP_NO")
    private String empNo;

}
