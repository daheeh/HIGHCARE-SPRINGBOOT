package com.highright.highcare.mypage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_MY_BSCD")
@Getter
@Setter
@ToString
public class MyBscd {

    @Id
    @Column(name = "BSCD_CODE")
    private int code;

    @Column(name = "BSCD_NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "COMPANY")
    private String company;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "CONTENT")
    private String content;

//    @Column(name = "EMP_NO")
//    private int empNo;

    // 하나의 회원이 명함 여러개를 가질 수 있다.
    @ManyToOne
    @JoinColumn(name = "EMP_NO")
    private MyEmployee myEmp;


}
