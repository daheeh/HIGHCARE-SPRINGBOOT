package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@Getter
public class ManagementDTO {

    private String manNo;
    private Timestamp startTime;
    private Timestamp endTime;
    private String manDate;
    private String status;
    private int empNo;
}
