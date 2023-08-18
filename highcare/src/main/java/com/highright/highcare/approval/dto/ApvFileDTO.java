package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFileDTO {

    private String apvNo;
    private String fileNo;
    private String originalFileName;
    private String savedFileName;

}
