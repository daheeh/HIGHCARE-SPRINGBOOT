package com.highright.highcare.admin.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ADMAuthAccountDTO {

    // 수정
    private String authCode;

    private String id;

}
