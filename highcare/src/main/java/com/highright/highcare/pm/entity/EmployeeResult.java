package com.highright.highcare.pm.entity;

import lombok.Getter;

@Getter
public class EmployeeResult {

    private int id;
    private String text;

    public EmployeeResult(Employees employees){
        this.id = employees.getEmpNo();
        this.text = employees.getEmpName();
    }


}
