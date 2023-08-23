package com.highright.highcare.admin.entity;

import com.highright.highcare.auth.entity.ADMAuthAccount;
import com.highright.highcare.auth.entity.ADMEmployee;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity(name = "adminAccount")
@Table(name="TBL_ACCOUNT")
@Getter
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

}
