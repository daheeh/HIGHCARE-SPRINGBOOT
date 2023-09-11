package com.highright.highcare.pm.entity;


import com.highright.highcare.mypage.entity.MyApvVation;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TBL_ANNUAL")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "PM_SEQ_NO",
        sequenceName = "DEPARTMENT_SEQ_NO",
        initialValue = 1, allocationSize = 1
)
@Setter
//@ToString
public class AnAnual implements Serializable {


    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PM_SEQ_NO")
    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name = "ANN_NO")
    private int annNo;

    @Id
    @Column(name = "APV_NO")
    private String ApvNo;

    @Column(name = "BASIC_ANNUAL")
    private int basicAnnual;

    @Column(name = "USE_ANNUAL")
    private int useAnnual;

    @Column(name = "ADD_ANNUAL")
    private int addAnnual;

    @Column(name = "TOTAL_ANNUAL")
    private int totalAnnual;

    @Column(name = "REASON")
    private String reason;

//    @ManyToOne
//    @JoinColumn(name = "EMP_NO", insertable = false, updatable = false)
//    private AnEmployee anEmployee;

    @ManyToOne
    @JoinColumn(name = "APV_NO", insertable = false, updatable = false)
    private Pmfoms pmForms;

//    @OneToOne
//    @JoinColumn(name = "APV_NO", insertable = false, updatable = false, referencedColumnName = "APV_NO")
//    private ApvVacationPm vacation;

    @ManyToOne
    @JoinColumn(name = "EMP_NO", insertable = false, updatable = false)
    private AnEmployee AnEmployee;


    // 기타 필드, 생성자, getter 및 setter 메서드
}

// 연차를 기준으로 조회해서 가져올거면 현재년도기준으로 조회 조건절...현재년도 2023 리액트에서 잘라서던지면
// changeevent
// 년도 계산해서 조회해서넘길것..
//