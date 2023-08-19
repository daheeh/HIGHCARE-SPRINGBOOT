package com.highright.highcare.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Setter
@Getter
@RedisHash(value="id", timeToLive = 60)     // 60초임 test
@ToString
public class RefreshToken {

    @Id
    private String id;
    private String refreshToken;
}
