package com.highright.highcare.approval.dto;
import lombok.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFormDTO {

    private Long apvNo;
    private String title;
    private Date writeDate;
    private String apvStatus;
    private char isUrgency;
    private String category;
    private String contents1;
    private String contents2;
    private int empNo;
    private List<ApvExpFormDTO> apvExpForms;
    private List<ApvVacationDTO> apvVacations;

}
