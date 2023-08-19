package com.highright.highcare.auth.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TBL_JOB")
@Getter
public class Job {

    @Id
    @Column(name="JOB_CODE")
    private int jobCode;

    @Column(name="NAME")
    private String jobName;

    @Override
    public String toString() {
        return "Job{" +
                "jobCode=" + jobCode +
                ", jobName='" + jobName + '\'' +
                '}';
    }
}
