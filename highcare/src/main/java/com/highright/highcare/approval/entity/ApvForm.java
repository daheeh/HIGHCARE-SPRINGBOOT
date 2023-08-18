package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_FORM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApvForm {

    @Id
    @Column(name = "")
    private String apvNo;
    private String title;
    private Date writeDate;
    private String apvStatus;
    private char isUrgency;
    private String contents1;
    private String contents2;
    private Number empNo;

}
