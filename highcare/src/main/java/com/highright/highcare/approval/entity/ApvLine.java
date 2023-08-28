package com.highright.highcare.approval.entity;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.PmEmployee;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_APV_LINE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_LINES",
        sequenceName = "SEQ_APV_LINES",
        initialValue = 1, allocationSize = 1
)
public class ApvLine {

    @Id
    @Column(name = "APV_LINE_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_LINES"
    )
    private Long apvLineNo;

    @Column(name = "DEGREE")
    private int degree;

    @Column(name = "ISAPPROVAL")
    private char isApproval;

    @Column(name = "APV_DATE")
    private String apvDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMP_NO")
    private PmEmployee employee;

}
