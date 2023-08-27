package com.highright.highcare.auth.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name="TBL_AUTH_ACCOUNT")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ADMAuthAccount implements Serializable {

    @Id
    @Column(name="AUTH_CODE")
    private String authCode;

    @Id
    @Column(name="ID")
    private String id;
}
