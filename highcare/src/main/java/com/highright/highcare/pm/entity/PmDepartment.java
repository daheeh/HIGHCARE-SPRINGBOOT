package com.highright.highcare.pm.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

//@SequenceGenerator(
//        name="DEPARTMENT_SEQ_GENERATOR",
//        sequenceName="SEQ_DEPARTMENT_CODE",
//        initialValue = 1, allocationSize = 1
//)
//@Getter
//@Setter
//@ToString
@Entity
@Getter
@Table(name="TBL_DEPARTMENT")
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name="UPPER_CODE", nullable = true)
    private Integer upperCode; // null값 받아주려면 Integer...

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="UPPER_CODE", insertable = false,updatable = false) // insert = false, update = false ==> 조회만하겠다
    private PmDepartment parent;
    // 자기자신을 join해야함..!셀프조인

    @OneToMany(mappedBy = "parent")
    private List<PmDepartment> children = new ArrayList<>();

}
// employee에서 department manytoone
// 한명의사원이; 하나의 부서를 가지고있음
// manytoone 부서를 연결
