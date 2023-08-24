package com.highright.highcare.pm.entity;

import lombok.Getter;

import java.util.stream.Collectors;

@Getter
public class DepartementResult {

    private int id;
    private Integer parent;
    private boolean droppable;
    private String text;

//    private List<>

    public DepartementResult(Departments departments){
        this.id = departments.getDeptCode();
        this.parent = departments.getUpperCode();
        this.droppable = true;
        this.text = departments.getName();

    }

}


