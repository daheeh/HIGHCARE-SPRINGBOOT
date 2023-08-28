package com.highright.highcare.bulletin.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BulletinEmployeeDTO {

    private int empNo;
    private String empName;
    private String empEmail;
    private String phone;
    private String residentNo;
    private Date startDate;
    private Date endDate;
    private char isResignation;
    private int deptCode;
    private int jobCode;
    private String address;
    private String education;
    private String telephone;

}
