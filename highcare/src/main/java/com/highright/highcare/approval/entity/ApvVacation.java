package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_VACATION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvVacation {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "APV_COMMENT")
    private String comment;

}
