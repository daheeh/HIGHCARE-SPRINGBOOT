package com.highright.highcare.mypage.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_EMPLOYEE")
@Getter
@ToString
@Setter
public class MyEmployee {

    @Id
    @Column(name = "EMP_NO")
    private int empNo;
    @Column(name = "EMP_NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "RESIDENT_NO")
    private String reNo;    // 주민번호
    @Column(name = "START_DATE")
    private String sDate;   // 입사일
//
//    private String eDate;   // 퇴사일
//
//    private String isRes;  // 퇴사여부

    @OneToOne
    @JoinColumn(name = "DEPT_CODE")
    private Department dep;

    @OneToOne
    @JoinColumn(name="JOB_CODE")
    private Job job;

    @Column(name = "ADDRESS")
    private String address;
    //    private String edu;
    @Column(name = "TELEPHONE")
    private String tel;    // 내선전화

    @OneToOne
    @JoinColumn(name = "DEPT_CODE", insertable = false, updatable = false)
    private Department deptName;

    @OneToOne
    @JoinColumn(name = "JOB_CODE", insertable = false, updatable = false)
    private Job jobName;







}
