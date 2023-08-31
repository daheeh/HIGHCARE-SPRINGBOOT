package com.highright.highcare.approval.dto;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvLineDTO {

    private Long apvLineNo;
    private int degree;
    private String isApproval;
    private String apvDate;

    private PmEmployeeDTO employee;
    private Long apvNo;



}
