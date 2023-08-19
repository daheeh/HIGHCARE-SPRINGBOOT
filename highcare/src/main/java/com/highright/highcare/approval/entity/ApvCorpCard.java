package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_APV_CORPORATE_CARD")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvCorpCard {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "CARD_NO")
    private String cardNo;

    @Column(name = "PAYMENT_MONTH")
    private Number paymentMonth;

}
