package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.example.vouchermanagement.application.voucher.VoucherStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class VoucherStorageConfig {
    @Bean
    VoucherStorage voucherStorage(final VoucherRepository voucherRepository) {
        return new VoucherSpringDataStorage(voucherRepository);
    }
}
