package com.highright.highcare.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthAccountDTO {

    private String authCode;

    private String id;

    private String groupCode;

}
