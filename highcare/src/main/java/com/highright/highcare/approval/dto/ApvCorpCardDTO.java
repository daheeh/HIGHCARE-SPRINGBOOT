package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvCorpCardDTO {

    private String apvNo;
    private String cardNo;
    private Number paymentMonth;

}
