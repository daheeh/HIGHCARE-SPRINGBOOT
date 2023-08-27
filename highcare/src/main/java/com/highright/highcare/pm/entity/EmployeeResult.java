package com.highright.highcare.pm.entity;

import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeResult {

    private int id;
    private String text;
    private int parent;
    private boolean droppable;
    private String jobName;
//    private String name;


    public EmployeeResult(Employees employeesList){
        this.id = employeesList.getEmpNo();
        this.text = employeesList.getEmpName();
        this.parent = employeesList.getJob().getUpperJobCode();
        this.droppable = true;
        this.jobName = employeesList.getJob().getName();



    }


}
