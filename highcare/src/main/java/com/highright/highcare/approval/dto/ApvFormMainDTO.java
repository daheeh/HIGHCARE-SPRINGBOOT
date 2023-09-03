package com.highright.highcare.approval.dto;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFormMainDTO {

    private Long apvNo;
    private String title;
    private Date writeDate;
    private String apvStatus;
    private String isUrgency;
    private String category;
    private String contents1;
    private String contents2;
    private int empNo;
    private PmEmployeeDTO employee;

}
