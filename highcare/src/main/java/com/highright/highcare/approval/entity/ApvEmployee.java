package com.highright.highcare.approval.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TBL_EMPLOYEE")
@Getter
public class ApvEmployee {

    @Id
    @Column(name="EMP_NO")
    private int empNo;

    @Column(name="EMP_NAME")
    private String empName;

    @OneToOne
    @JoinColumn(name="DEPT_CODE")
    private ApvDepartment deptCode;

    @Column(name="PHONE")
    private String phone;

    @Column(name="EMAIL")
    private String email;

    @OneToOne
    @JoinColumn(name="JOB_CODE")
    private ApvJob jobCode;

    @Override
    public String toString() {
        return "ApvEmp: " +
                "empNo=" + empNo +
                ", empName='" + empName +
                ", deptCode=" + deptCode +
                ", jobCode=" + jobCode +
                ", deptName=" + deptCode.getDeptName() +
                ", jobName=" + jobCode.getJobName() +
                '\'';
    }
}