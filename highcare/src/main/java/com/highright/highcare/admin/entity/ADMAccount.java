package com.highright.highcare.admin.entity;

import com.highright.highcare.admin.dto.AccessManagerDTO;
import com.highright.highcare.auth.dto.AuthAccountDTO;
import com.highright.highcare.auth.entity.AUTHAccount;
import com.highright.highcare.auth.entity.AUTHAuthAccount;
import com.highright.highcare.auth.entity.AUTHEmployee;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="TBL_ACCOUNT")
@Getter
@Setter
@ToString
public class ADMAccount {

    @Id
    @Column(name="ID")
    private String memberId;

    @Column(name="EMP_NO")
    private int empNo;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="IS_TEMP_PWD")
    private String isTempPwd;

    @Column(name="PWD_EXPIRED_DATE")
    private Date pwdExpiredDate;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="ID")
    private List<AUTHAuthAccount> roleList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="EMP_NO", insertable = false, updatable = false)
    private AUTHEmployee employee;

    @OneToOne
    @JoinColumn(name="ID", insertable = false, updatable = false)
    private AccessManager accessManager;

}