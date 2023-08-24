package com.highright.highcare.pm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name ="TBL_DEPARTMENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Departments {

    @Id
    @Column(name = "DEPT_CODE")
    private int deptCode;

    @Column(name="NAME")
    private String name;

    @Column(name="TEL")
    private String tel;

    @Column(name="UPPER_NAME")
    private String upperName;

    @Column(name="UPPER_CODE")
    private Integer upperCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPPER_CODE", insertable = false, updatable = false)
    private PmDepartment parent;

    @OneToMany(mappedBy = "parent")
    private List<PmDepartment> children = new ArrayList<>();

    @OneToMany(mappedBy = "deptCode")
    private List<Employees> employees;

}
