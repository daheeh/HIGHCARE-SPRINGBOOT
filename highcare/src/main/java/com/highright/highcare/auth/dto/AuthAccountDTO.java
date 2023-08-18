package com.highright.highcare.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthAccountDTO {

    private String authCode;

    private String id;

    private String groupCode;

}
