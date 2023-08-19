package com.highright.highcare.auth.entity;

import com.highright.highcare.auth.dto.AuthDTO;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="TBL_ACCOUNT")
@Getter
@ToString
public class Account {

    @Id
    @Column(name="ID")
    private String memberId;

//    @Column(name="EMP_NO")
//    private int empNo;

    @OneToOne
    @JoinColumn(name="EMP_NO")
    private Employee employee;

    @Column(name="PASSWORD")
    private String password;

//    @OneToMany(mappedBy = "account" )
    @OneToMany
    @JoinColumn(name="ID")
    private List<AuthAccount> roleList;

//    @Override
//    public String toString() {
//        return "Account{" +
//                "memberId='" + memberId + '\'' +
//                ", employee=" + employee +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
