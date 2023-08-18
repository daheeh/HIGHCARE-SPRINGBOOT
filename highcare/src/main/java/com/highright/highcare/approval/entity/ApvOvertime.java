package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "TBL_APV_OVERTIME")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvOvertime {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "WORKING_DATE")
    private Date workingDate;

    @Column(name = "START_TIME")
    private Time startTime;

    @Column(name = "END_TIME")
    private Time endTime;

    @Column(name = "REASON")
    private String reason;

}
