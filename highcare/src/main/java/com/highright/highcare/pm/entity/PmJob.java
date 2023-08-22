//package com.highright.highcare.pm.entity;
//
//import lombok.*;
//import org.checkerframework.checker.units.qual.C;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
////@SequenceGenerator(
////        name="JOB_SEQ_GENERATOR",
////        sequenceName = "SEQ_JOB_CODE",
////        initialValue = 1, allocationSize = 1
////)
////@ToString
//@Entity
//@Table(name="TBL_JOB")
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class PmJob {
//
//    @Id
//    @Column(name="JOB_CODE")
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "JOB_SEQ_GENERATOR"
//    )
//    private int jobCode;
//
//    @Column(name="NAME")
//    private String name;
//
//    @Column(name="UPPER_JOB_CODE", nullable = true)
//    private Integer upperJobCode;
//
//    @Column(name="UPPER_JOB_NAME")
//    private String upperJobName;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "UPPER_JOB_CODE", insertable = false,updatable = false)
//    private PmJob pmJob;
//
//    @OneToMany(mappedBy = "parent")
//    private List<PmJob> children = new ArrayList<>();
//}
