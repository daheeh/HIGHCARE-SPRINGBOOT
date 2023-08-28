package com.highright.highcare.auth.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RefreshTokenDTO {

    private String id;                  // 회원아이디
    private String refreshToken;    // 리프레쉬토큰
}
