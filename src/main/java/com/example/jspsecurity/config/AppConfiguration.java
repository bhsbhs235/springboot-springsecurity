package com.example.jspsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;

@Configuration
public class AppConfiguration{
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}