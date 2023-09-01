package com.highright.highcare.mypage.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_ANNUAL")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
public class MyAnnual  {

    @Id
    @Column(name = "EMP_NO")
    private int empNo;

    @Column(name ="BASIC_ANNUAL")
    private int bAn;
    @Column(name ="USE_ANNUAL")
    private int useAn;
    @Column(name ="ADD_ANNUAL")
    private int addAn;
    @Column(name ="TOTAL_ANNUAL")
    private int totalAn;

    @Column(name = "ANN_NO")
    private int annNo;

    @Column(name="APV_NO")
    private String  apvNo;


    // referencedColumnName : iITEM_NO랑 계속 매핑되어서 이걸 해줬더니 올바르게 매핑됨
    @OneToOne
    @JoinColumn(name = "APV_NO", insertable = false, updatable = false, referencedColumnName = "APV_NO")
    private MyApvVation myApvVation;

//    @Override
//    public String toString() {
//        return "MyAnnual{" +
//                "empNo=" + empNo +
//                ", bAn=" + bAn +
//                ", useAn=" + useAn +
//                ", addAn=" + addAn +
//                ", totalAn=" + totalAn +
//                ", apvNo='" + apvNo + '\'' +
//                '}';
//    }
}

