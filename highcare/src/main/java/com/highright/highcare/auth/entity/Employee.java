package com.highright.highcare.auth.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="TBL_EMPLOYEE")
@Getter
public class Employee {

    @Id
    @Column(name="EMP_NO")
    private int empNo;

    @Column(name="NAME")
    private String name;

    @OneToOne
    @JoinColumn(name="DEPT_CODE")
    private Department deptCode;

    @OneToOne
    @JoinColumn(name="JOB_CODE")
    private Job jobCode;

    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", name='" + name + '\'' +
                ", deptCode=" + deptCode +
                ", jobCode=" + jobCode +
                '}';
    }
}
