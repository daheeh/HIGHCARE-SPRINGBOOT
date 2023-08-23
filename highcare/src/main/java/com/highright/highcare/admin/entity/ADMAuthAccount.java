package com.highright.highcare.admin.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "adminAuthAccount")
@Table(name="TBL_AUTH_ACCOUNT")
@Getter
@Setter
@ToString
public class ADMAuthAccount implements Serializable {

    private static final long serialVersionUID = 1L; // 추가

    @Id
    @Column(name="AUTH_CODE")
    private String authCode;

    @Id
    @Column(name="ID")
    private String id;



}
