package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class MilitaryDTO {

    private Integer milNo;
    private int empNo;
    private String status;
    private char isWhether;

}
