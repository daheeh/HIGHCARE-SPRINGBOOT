package com.highright.highcare.approval.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_APV_CORPORATE_CARD_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvCorpCardDetailDTO {

    @Id
    @Column(name = "ITEMS_NO")
    private String itemsNo;

    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "ACCOUNT")
    private String account;

    @Column(name = "AMOUNT")
    private Number amount;

    @Column(name = "CARD_COMMENT")
    private String cardComment;


}
