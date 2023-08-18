package com.highright.highcare.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Setter
@Getter
@RedisHash(value="refreshToken", timeToLive = 60)
public class RefreshToken {

    @Id
    private String id;
    private String refreshToken;
}
