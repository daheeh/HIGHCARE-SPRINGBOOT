package com.highright.highcare.pm.dto;

import com.highright.highcare.approval.dto.ApvLineDTO;
import com.highright.highcare.pm.entity.Career;
import com.highright.highcare.pm.entity.Certification;
import com.highright.highcare.pm.entity.Military;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PmEmployeeDTO {

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
    private DepartmentDTO dt;
//    private JobDTO job;
//    private MilitaryDTO military;
//    private CareerDTO career;
//    private CertificationDTO certification;
    private List<MilitaryDTO> military;
    private List<CareerDTO> career;
    private List<CertificationDTO> certification;
}
