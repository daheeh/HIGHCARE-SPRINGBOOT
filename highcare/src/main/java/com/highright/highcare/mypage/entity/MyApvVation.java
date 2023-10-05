package com.highright.highcare.mypage.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_VACATION")
@Getter
@SequenceGenerator(
        name = "MyAPV_VACATION_NO",
        sequenceName = "APV_VACATION_NO",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class MyApvVation implements Serializable {

    @Id
    @Column(name="ITEMS_NO")

    private String itemNo;

    @Column(name="APV_NO")
    private String apvNo;

    @Column(name="TYPE")
    private String type;

    @Column(name="START_DATE")
    private String sdate;

    @Column(name="END_DATE")
    private String edate;

    @Column(name="APV_COMMENT")
    private String comment;

    @Column(name="OFFTYPE1")
    private String off1;

    @Column(name="OFFTYPE2")
    private String off2;


}
