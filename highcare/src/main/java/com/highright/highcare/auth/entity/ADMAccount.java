package com.highright.highcare.auth.entity;

import com.highright.highcare.chatting.service.ADMAccountEntityListener;
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
@EntityListeners(ADMAccountEntityListener.class)
public class ADMAccount {

    @Id
    @Column(name="ID")
    private String memberId;

    @OneToOne
    @JoinColumn(name="EMP_NO")
    private ADMEmployee employee;

    @Column(name="PASSWORD")
    private String password;

    @OneToMany
    @JoinColumn(name="ID")
    private List<ADMAuthAccount> roleList;





}
