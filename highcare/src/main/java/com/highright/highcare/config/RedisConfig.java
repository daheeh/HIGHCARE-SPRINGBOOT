package com.highright.highcare.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EntityScan(basePackages = "com.highright.highcare")
@EnableRedisRepositories(basePackages = "com.highright.highcare")
public class RedisConfig {
}