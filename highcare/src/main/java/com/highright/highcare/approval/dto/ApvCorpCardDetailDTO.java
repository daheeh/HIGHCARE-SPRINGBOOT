package com.highright.highcare.approval.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvCorpCardDetailDTO {

    private String itemsNo;
    private String apvNo;
    private String details;
    private String account;
    private Number amount;
    private String cardComment;


}
