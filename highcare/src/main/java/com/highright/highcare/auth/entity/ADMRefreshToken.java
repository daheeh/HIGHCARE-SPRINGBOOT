package com.highright.highcare.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Setter
@Getter
@RedisHash(value="id", timeToLive = 600)     // 10분 test
@ToString
@Builder
public class ADMRefreshToken {

    @Id
    private String id;
    private String refreshToken;
}
