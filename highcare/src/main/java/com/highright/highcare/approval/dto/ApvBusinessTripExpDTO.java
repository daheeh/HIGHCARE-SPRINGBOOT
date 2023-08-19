package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvBusinessTripExpDTO {

    private String apvNo;
    private Number location;
    private Number personNum;
    private String period;
    private String refApvNo;
}
