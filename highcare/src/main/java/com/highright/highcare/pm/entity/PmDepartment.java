package com.highright.highcare.pm.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="TBL_DEPARTMENT")
@SequenceGenerator(
        name="DEPARTMENT_SEQ_GENERATOR",
        sequenceName="SEQ_DEPARTMENT_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PmDepartment {

    @Id
    @Column(name="DEPT_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "DEPARTMENT_SEQ_GENERATOR"
    )
    private int deptCode;

    @Column(name = "NAME")
    private String name;

    @Column(name="TEL")
    private String tel;

    @Column(name="UPPER_NAME")
    private String upperName;

    @Column(name="UPPER_CODE")
    private int upperCode;
}
