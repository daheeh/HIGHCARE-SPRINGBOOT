package com.highright.highcare.pm.entity;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="TBL_JOB")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PmJob {

    @Id
    @Column(name = "JOB_CODE")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "JOB_SEQ_GENERATOR"
    )
    private int jobCode;

    @Column(name="NAME")
    private String name;

    @Column(name="UPPER_JOB_CODE", nullable = true)
    private Integer upperJobCode;

    @Column(name="UPPER_JOB_NAME")
    private String upperJobName;
}
