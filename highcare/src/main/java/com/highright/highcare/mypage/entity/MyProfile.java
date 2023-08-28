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
//@ToString
public class MyProfile {

    @Id
    @Column(name = "PROFILE_CODE")
    private int profileCode;

    @Column(name="EMP_NO")
    private int empNo;

    @Column(name = "PROFILE_PHOTO")
    private String photo;

    // MyProfile은 MyProfileFile을 알고 있어야 한다.
    // MyProfileFile타입의 List로 조인

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="PROFILE_CODE")
    private List<MyProfileFile> profileFile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMP_NO", insertable = false, updatable = false)
    private MyEmployee myEmployee;

    @Override
    public String toString() {
        return "MyProfile{" +
                "profileCode=" + profileCode +
                ", empNo=" + empNo +
                ", photo='" + photo + '\'' +
                ", myEmployee=" + myEmployee +
                '}';
    }
}
