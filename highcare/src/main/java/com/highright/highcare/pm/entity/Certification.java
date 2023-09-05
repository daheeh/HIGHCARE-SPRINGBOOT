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
@Table(name="TBL_CERTIFICATION")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Certification {

    @Id
    @Column(name = "CER_NO")
    private Integer cerNo;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "INSTITUTION")
    private String institution;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMP_NO")
    private int empNo;

}
