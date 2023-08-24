package com.highright.highcare.pm.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name="TBL_EMPLOYEE")
@SequenceGenerator(
        name="EMPLOYEE_SEQ_GENERATOR",
        sequenceName = "SEQ_EMPLOYEE_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employees {
    @Id
    @Column(name = "EMP_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EMPLOYEE_SEQ_GENERATOR"
    )
    private int empNo;

    @Column(name = "NAME")
    private String empName;

    @Column(name = "EMAIL")
    private String empEmail;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "RESIDENT_NO")
    private String residentNo;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "IS_RESIGNATION")
    private char isResignation;

    @Column(name = "DEPT_CODE")
    private int deptCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EDUCATION")
    private String education;

    @Column(name = "TELEPHONE")
    private String telephone;

    @ManyToOne
    @JoinColumn(name="JOB_CODE")
    private PmJob job;


}


