package com.greedy.bootrestapi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /* 엔티티와 DTO변환을 위한 modelMapper 빈 설정 */
    @Bean
    public ModelMapper modelMapper(){

        return new ModelMapper();
    }
}
