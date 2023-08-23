package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

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
    private Time startDate;

    @Column(name = "END_DATE")
    private Time endDate;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "APV_COMMENT")
    private String comment;

    @Column(name = "AMOUNT")
    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;
}
