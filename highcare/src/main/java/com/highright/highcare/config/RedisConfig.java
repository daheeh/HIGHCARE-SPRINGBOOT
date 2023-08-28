package com.highright.highcare.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EntityScan(basePackages = "com.highright.highcare")
@EnableRedisRepositories(basePackages = "com.highright.highcare")
public class RedisConfig {
<<<<<<< HEAD
}
=======
}
>>>>>>> 4c170c2b8ba36b7a04f3dc1a13c2fd927f9c8d47
