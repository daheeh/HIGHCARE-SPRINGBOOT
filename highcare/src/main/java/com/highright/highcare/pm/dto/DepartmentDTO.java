package com.highright.highcare.pm.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class DepartmentDTO {

    private int deptCode;
    private String name;
    private String tel;
    private String upperName;
    private int upperCode;
}
