package com.highright.highcare.pm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;


@Entity
@Table(name="TBL_CAREER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Career {

    @Id
    @Column(name = "CAR_NO")
    private Integer carNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HISTORY")
    private int history;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "JOB")
    private String job;

    @Column(name = "EMP_NO")
    private int empNo;
}
