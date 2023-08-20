package com.highright.highcare.approval.entity;

import com.highright.highcare.approval.dto.ApvExpenseDTO;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_APV_FORM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "APV_SEQ_NO",
        sequenceName = "SEQ_APV_NO",
        initialValue = 1, allocationSize = 1
)
public class ApvForm {

    @Id
    @Column(name = "APV_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "APV_SEQ_NO"
    )
    private Long apvNo;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Column(name = "APV_STATUS")
    private String apvStatus;

    @Column(name = "ISURGENCY")
    private char isUrgency;

    @Column(name = "APV_CATEGORY")
    private String category;

    @Column(name = "CONTENTS1")
    private String contents1;

    @Column(name = "CONTENTS2")
    private String contents2;

    @Column(name = "EMP_NO")
    private int empNo;

//    @OneToOne(mappedBy = "apvForm", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
//    @Where(clause = "category = '지출'")
//    private ApvExpense apvExpense;

    @OneToMany(mappedBy = "apvForm", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ApvExpForm> apvExpForms = new ArrayList<>();

}
