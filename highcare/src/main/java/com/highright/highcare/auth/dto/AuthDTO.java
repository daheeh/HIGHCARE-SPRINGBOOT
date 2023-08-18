package com.highright.highcare.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthDTO {

    String authCode;
    String authName;
    String authDesc;

}
