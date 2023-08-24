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

    private String authCode;

    private String id;

}
