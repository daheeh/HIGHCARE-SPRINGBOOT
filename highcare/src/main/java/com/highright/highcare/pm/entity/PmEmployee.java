package com.highright.highcare.pm.entity;

import com.highright.highcare.pm.entity.PmDepartment;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "dtype")
@Table(name="TBL_EMPLOYEE")
@SequenceGenerator(
        name="EMPLOYEE_SEQ_GENERATOR",
        sequenceName = "SEQ_EMPLOYEE_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PmEmployee {
    @Id
    @Column(name = "EMP_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "EMPLOYEE_SEQ_GENERATOR"
    )
    private int empNo;

    @Column(name = "NAME")
    private String empName;

    @Column(name = "EMAIL")
    private String empEmail;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "RESIDENT_NO")
    private String residentNo;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "IS_RESIGNATION")
    private char isResignation;

    @Column(name = "DEPT_CODE")
    private int deptCode;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="parent")
//    private PmDepartment deptCode;

    @Column(name = "JOB_CODE")
    private int jobCode;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EDUCATION")
    private String education;

    @Column(name = "TELEPHONE")
    private String telephone;

//    @OneToMany(mappedBy = "parent")
//    private List<PmEmployee> children = new ArrayList<>();
//    @Id
//    @GeneratedValue
//    @Column(name="emp_No")
//    private int empNo; // 사원번호
//    private String empName; // 사원명
//    private String empEmail; // 이메일
//    private String phone; //핸드폰번호
//    private int deptCode; // 부서
//    private int jobCode; // 직급
//    private String telephone; // 내선전화

}

// 임시로 값넣고
// 사원조회페이지에서 조회먼저해보기
// 이거넘겨줄때 상세페이지에서 값 필요하니까 안보이게 같이넘겨줘야함
