package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "TBL_APV_VACATION")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "APV_SEQ_ITEMS",
        sequenceName = "SEQ_APV_VA_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvVacation {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "APV_SEQ_ITEMS"
    )
    private String itemNo;

    @Column(name = "START_DATE")
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "APV_COMMENT")
    private String comment;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "OFFTYPE1")
    private Time offType1;

    @Column(name = "OFFTYPE2")
    private Time offType2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;
}
