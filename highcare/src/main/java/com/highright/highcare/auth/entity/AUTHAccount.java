package com.highright.highcare.auth.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TBL_ACCOUNT")
@Getter
@Setter
@ToString
public class AUTHAccount {

    @Id
    @Column(name="ID")
    private String memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMP_NO", insertable = false, updatable = false)
    private AUTHEmployee employee;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="IS_TEMP_PWD")
    private String isTempPwd;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ID")
    private List<AUTHAuthAccount> roleList;







}
