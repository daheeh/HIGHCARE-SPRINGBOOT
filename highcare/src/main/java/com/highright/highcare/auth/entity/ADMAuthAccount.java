package com.highright.highcare.auth.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
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

    @Id
    @Column(name="GROUP_CODE")
    private String groupCode;


}
