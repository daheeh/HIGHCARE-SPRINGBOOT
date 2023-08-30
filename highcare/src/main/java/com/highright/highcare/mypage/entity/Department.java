package com.highright.highcare.mypage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "TBL_DEPARTMENT")
@Getter
@Setter
@SequenceGenerator(
        name = "DEPARTMENT_SEQ_NO",
        sequenceName = "DEPARTMENT_SEQ_NO",
        initialValue = 1, allocationSize = 1
)
@ToString
public class Department {

    @Id
    @Column(name = "DEPT_CODE")
    private int code;

    @Column(name = "NAME")
    private String name;

//    @Override
//    public String toString() {
//        return "Department{" +
//                "name='" + name + '\'' +
//                '}';
//    }
}
