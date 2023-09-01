
package com.highright.highcare.mypage.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "TBL_EMPLOYEE")
@Getter
@ToString
@Setter
public class AnnEmployee {

    @Id
    @Column(name = "EMP_NO")
    private int empNo;


    // MyAnnual매핑
    @OneToMany
    @JoinColumn(name = "EMP_NO")
    private List<MyAnnual> myAnnual;




}
