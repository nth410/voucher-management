package com.example.vouchermanagement.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
class ClockConfig {

    @Bean
    Clock clock() {
        return Clock.system(ZoneId.of("Europe/Budapest"));
    }
}
