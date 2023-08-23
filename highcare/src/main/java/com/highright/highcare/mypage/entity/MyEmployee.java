package com.highright.highcare.mypage.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TBL_EMPLOYEE")
@Getter
@ToString
@Setter
public class MyEmployee {

    @Id
    @Column(name = "EMP_NO")
    private int empNo;
    @Column(name = "NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "RESIDENT_NO")
    private String reNo;    // 주민번호
    @Column(name = "START_DATE")
    private String sDate;   // 입사일

    //    private String eDate;
//    private String isRes;  // 퇴사여부

    @Column(name = "DEPT_CODE")
    private int deptCode;
    @Column(name = "JOB_CODE")
    private int jopCode;
    @Column(name = "ADDRESS")
    private String address;
    //    private String edu;
    @Column(name = "TELEPHONE")
    private String tel;    // 내선전화



}
