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
public class CareerDTO {

    private int carNo;
    private String name;
    private int history;
    private Date startDate;
    private Date endDate;
    private String job;
    private int empNo;

}
