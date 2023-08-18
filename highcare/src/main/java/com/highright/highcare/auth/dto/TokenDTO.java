package com.highright.highcare.auth.dto;

import lombok.*;

/* 토큰 정보 담을 객체*/
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class TokenDTO {

    private String grantType;
    private String memberName;
    private String id;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
