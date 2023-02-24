package com.example.vouchermanagement.application.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    IdGenerator idGenerator() {
        return new IdGeneratorImpl();
    }
}
