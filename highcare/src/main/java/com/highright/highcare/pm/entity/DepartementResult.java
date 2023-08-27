package com.highright.highcare.pm.entity;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class DepartementResult {

    private int id;
    private Integer parent;
    private boolean droppable;
    private String text;
    private String jobName;
    private String name;


//    private List<>

    public DepartementResult(Departments department,List<Employees> employeesList ){
        this.id = department.getDeptCode();
        this.parent = department.getUpperCode();
        this.droppable = true;
        this.text = department.getName();


        if (employeesList != null && !employeesList.isEmpty()) {
            this.name = employeesList.get(0).getEmpName();
            this.jobName = employeesList.get(0).getJob().getName();


    }


}}



//    public DepartementResult(Employees employeesList){
//        this.name =  employeesList.getEmpName();
//        this.jobName = employeesList.getJob().getName();
//    }

//
//        if (department.getDeptCode() > 10) {
//            this.id = employeesList.get(0).getEmpName();  // Use the name from the first employee in the list
//            this.text = employeesList.get(0).getEmpName();
//        } else {
//            this.text = department.getName();  // Use the department name
//            this.id = department.getDeptCode();
//        }
//        if (employeesList != null && !employeesList.isEmpty()) {
//            this.name = employeesList.get(0).getEmpName();  // For example, using the first employee's name
//            this.jobName = employeesList.get(0).getJob().getName();  // For example, using the first employee's job name
//        }
//
//        if (department.getDeptCode() > 20 && employeesList != null && !employeesList.isEmpty()) {
//            for (Employees employee : employeesList) {
//                if (employee.getEmpNo() > 11) {
//                    this.id = employee.getEmpNo();
//                    this.text = employee.getEmpName();
//                    break;
//                }
//            }
//        } else {
//            this.id = department.getDeptCode();
//            this.text = department.getName();
//        }
