package com.highright.highcare.pm.entity;


import com.highright.highcare.mypage.entity.MyApvVation;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

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
        sequenceName = "SEQ_ANN_NO",
        initialValue = 1, allocationSize = 1
)
@Setter
@DynamicInsert
public class AnAnual implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PM_SEQ_NO"
    )
    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name = "ANN_NO")
    private int annNo;

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


    @OneToMany
    @JoinColumn(name = "APV_NO", insertable = false, updatable = false, referencedColumnName = "APV_NO")
    private List<ApvVacationPm> vacation;


    @OneToMany
    @JoinColumn(name = "EMP_NO", insertable = false, updatable = false,referencedColumnName = "EMP_NO")
    private List<AnEmployee> AnEmployee;


}
