package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_APV_ISSUANCE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_ITEMS",
        sequenceName = "SEQ_APV_IN_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvIssuance {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS"
    )
    private Long itemsNo;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SUBTYPE")
    private String subType;

    @Column(name = "SUBMISSION")
    private String submission;

    @Column(name = "USES")
    private String uses;

    @Column(name = "REQUESTS")
    private String requests;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;

}
