//package com.highright.highcare.pm.dto;
//
//import com.highright.highcare.pm.entity.PmEmployee;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.sql.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class PmEmployeeResult {
//
//    private int empNo;
//    private String empName;
//    private String empEmail;
//    private String phone;
//    private String residentNo;
//    private Date startDate;
//    private Date endDate;
//    private char isResignation;
//    private int deptCode;
//    private int jobCode;
//    private String address;
//    private String education;
//    private String telephone;
//    private List<PmEmployeeResult> children;
//
//    public static PmEmployeeResult of(PmEmployee pmEmployee){
//        return new PmEmployeeResult(
//                pmEmployee.getEmpNo(),
//                pmEmployee.getEmpName(),
//                pmEmployee.getEmpEmail(),
//                pmEmployee.getPhone(),
//                pmEmployee.getResidentNo(),
//                pmEmployee.getStartDate(),
//                pmEmployee.getEndDate(),
//                pmEmployee.getIsResignation(),
//                pmEmployee.getDeptCode(),
//                pmEmployee.getJobCode(),
//                pmEmployee.getAddress(),
//                pmEmployee.getEducation(),
//                pmEmployee.getTelephone(),
//                pmEmployee.getChildren().stream().map(PmEmployeeResult::of).collect(Collectors.toList())
//
//        );
//    }
//}
