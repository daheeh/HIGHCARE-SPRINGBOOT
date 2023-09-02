package com.highright.highcare.pm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="TBL_MANAGEMENT")
@SequenceGenerator(
        name="MANAGEMENT_SEQ_GENERATOR",
        sequenceName = "SEQ_MANAGEMENT_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Management {

    @Id
    @Column(name = "MAN_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "MANAGEMENT_SEQ_GENERATOR"
    )
    private String manNo;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "MAN_DATE")
    private String manDate;

    @Column(name = "STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "EMP_NO")
    private PmEmployee mgemployee;
}
