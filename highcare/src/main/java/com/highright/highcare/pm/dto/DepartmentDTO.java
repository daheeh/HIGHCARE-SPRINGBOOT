package com.highright.highcare.pm.dto;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private int deptCode;
    private String name;
    private String tel;
    private String upperName;
    private int upperCode;


}
