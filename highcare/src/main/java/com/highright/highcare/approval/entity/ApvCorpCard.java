package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_APV_CORPORATE_CARD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_ITEMS02",
        sequenceName = "SEQ_APV_CC_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvCorpCard {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS02"
    )
    private Long itemsNo;

    @Column(name = "CARD_NO")
    private String cardNo;

    @Column(name = "PAYMENT_MONTH")
    private int paymentMonth;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "CARD_COMMENT")
    private String cardComment;

    @Column(name = "APV_NO")
    private Long apvNo;
}
