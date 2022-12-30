package com.svalero.library.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BooksConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
