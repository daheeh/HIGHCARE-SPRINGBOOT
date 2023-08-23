package com.highright.highcare.mypage.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "TBL_MY_PROFILE")
@Getter
@Setter
@SequenceGenerator(
        name = "PROFILE_SEQ_NO",
        sequenceName = "PROFILE_SEQ_NO",
        initialValue = 1, allocationSize = 1
)
@ToString
public class MyProfile {

    @Id
    @Column(name = "PROFILE_CODE")
    private int profileCode;

    @Column(name="EMP_NO")
    private int empNo;

    @Column(name = "PHONE")
    private String phone;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PROFILE_PHOTO")
    private String photo;
    @Column(name = "CH_PHOTO")
    private String chPhoto;
    @Column(name = "ADDRESS")
    private String address;

//    @OneToOne
//    private MyEmployee myEmp;
//    @OneToOne
//    @JoinColumn(name="PROFILE_CODE")
//    private MyProfileFile profileFile;

    // MyProfile은 MyProfileFile을 알고 있어야 한다.
    // MyProfileFile타입의 List로 조인
    @OneToMany
    @JoinColumn(name="PROFILE_CODE")
    private List<MyProfileFile> profileFile;

}
