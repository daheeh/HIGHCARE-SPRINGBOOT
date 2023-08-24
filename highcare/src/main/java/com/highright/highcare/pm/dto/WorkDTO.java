package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.sql.Date;

@Embeddable
@Getter
public class WorkDTO {

    private int wsdNo;
    private String title;
    private Date startDate;
    private Date endDate;
    private String place;
    private String attendees;
    private String content;

}
