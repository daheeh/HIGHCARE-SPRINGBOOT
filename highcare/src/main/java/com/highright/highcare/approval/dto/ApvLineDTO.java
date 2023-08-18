package com.highright.highcare.approval.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvLineDTO {

    private String apvLineNo;
    private String degree;
    private char isApproval;
    private String apvNo;
    private String empNo;

}
