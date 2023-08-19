package com.highright.highcare.approval.dto;
import lombok.*;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFormDTO {

    private String apvNo;
    private String title;
    private Date writeDate;
    private String apvStatus;
    private char isUrgency;
    private String category;
    private String contents1;
    private String contents2;
    private int empNo;
}
