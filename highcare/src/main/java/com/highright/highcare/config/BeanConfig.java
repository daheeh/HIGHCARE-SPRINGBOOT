package com.highright.highcare.config;

import com.highright.highcare.common.AdminCustomBean;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public AdminCustomBean customBean(){
        return new AdminCustomBean();
    }

}
