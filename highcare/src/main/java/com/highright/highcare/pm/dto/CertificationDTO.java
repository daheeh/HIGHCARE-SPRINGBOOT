package com.highright.highcare.pm.dto;

import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Date;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDTO {

    private String name;
    private Date startDate;
    private Date endDate;
    private String institution;
    private int cerNo;
    private int empNo;
}
