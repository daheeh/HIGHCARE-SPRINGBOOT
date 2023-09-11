package com.highright.highcare.pm.dto;

import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacationDTO {

    private String ItemNo;    // pk
    private String ApvNo;      // fk로 조인
    private String type;    // 휴가종류 : 반차, 연차
    private Date startDate;
    private Date endDate;
    private String comment;
    private String off1;    // 반차
    private String off2;    // 반차끝
}
