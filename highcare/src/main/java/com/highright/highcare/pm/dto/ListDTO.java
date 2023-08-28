package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class ListDTO {

    private int wdsNo;
    private int empNo;
}
