package com.highright.highcare.approval.dto;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApvLineDTO {

    private Long apvLineNo;
    private int degree;
    private String isApproval;
    private Date apvDate;

    private PmEmployeeDTO employee;
    private Long apvNo;

    private int empNo;

    @Override
    public String toString() {
        return "ApvLineDTO{" +
                "apvLineNo=" + apvLineNo +
                ", degree=" + degree +
                ", isApproval='" + isApproval + '\'' +
                ", apvDate='" + apvDate + '\'' +
                ", apvNo=" + apvNo +
                ", empNo=" + empNo +
                '}';
    }
}
