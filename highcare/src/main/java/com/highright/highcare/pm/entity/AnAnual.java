package com.highright.highcare.pm.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="TBL_ANNUAL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnAnual implements Serializable {

    @Id
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

    @ManyToOne
    @JoinColumn(name = "EMP_NO", insertable = false, updatable = false)
    private AnEmployee anEmployee;

    // 기타 필드, 생성자, getter 및 setter 메서드
}
