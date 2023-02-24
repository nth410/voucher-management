package com.example.vouchermanagement.infrastructure.persistence.redemption;

import com.example.vouchermanagement.application.redemption.RedemptionStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedemptionStorageConfig {
    @Bean
    RedemptionStorage redemptionStorage(final RedemptionRepository redemptionRepository) {
        return new RedemptionSpringDataStorage(redemptionRepository);
    }
}
