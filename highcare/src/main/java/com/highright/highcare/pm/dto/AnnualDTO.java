package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class AnnualDTO {

    private String apvNo;
    private int empNo;
    private int basicAnnual;
    private int useAnnual;
    private int addAnnual;
    private int totalAnnual;
    private String reason;
}
