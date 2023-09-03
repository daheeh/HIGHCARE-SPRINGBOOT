package com.highright.highcare.pm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="TBL_MILITARY")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Military {

    @Id
    @Column(name = "MIL_NO")
    private Integer milNo;

    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "IS_WHETHER")
    private char isWhether;

    @OneToMany
    @Column(name = "EMP_NO")
    private List<PmEmployee> pmEmployee;
}
