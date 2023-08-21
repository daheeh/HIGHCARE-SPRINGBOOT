package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class JobDTO {

    private int jobCode;
    private String name;
}
