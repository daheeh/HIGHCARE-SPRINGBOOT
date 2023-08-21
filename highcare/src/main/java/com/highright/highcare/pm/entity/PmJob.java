package com.highright.highcare.pm.entity;

import lombok.*;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;

@Entity
@Table(name="TBL_JOB")
@SequenceGenerator(
        name="JOB_SEQ_GENERATOR",
        sequenceName = "SEQ_JOB_CODE",
        initialValue = 1, allocationSize = 1
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PmJob {

    @Id
    @Column(name="JOB_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "JOB_SEQ_GENERATO"
    )
    private int jobCode;

    @Column(name="NAME")
    private String name;
}
