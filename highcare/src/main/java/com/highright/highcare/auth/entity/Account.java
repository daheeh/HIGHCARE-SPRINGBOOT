package com.highright.highcare.auth.entity;

import com.highright.highcare.auth.dto.AuthDTO;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TBL_ACCOUNT")
@Getter
public class Account {

    @Id
    @Column(name="ID")
    private String memberId;

//    @Column(name="EMP_NO")
//    private int empNo;

    @OneToOne
    @JoinColumn(name="EMP_NO")
    private Employee employee;

    @Column(name="PASSWORD")
    private String password;

    @OneToMany
    @JoinColumn(name="ID")
    private List<AuthAccount> roleList;



}
