package com.highright.highcare.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.highright.highcare")
@EnableJpaRepositories(basePackages = "com.highright.highcare")
public class JpaConfig {
}
