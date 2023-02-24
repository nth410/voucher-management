package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.application.common.IdGenerator;
import com.example.vouchermanagement.application.voucher.VoucherStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class RedemptionServiceConfig {

    @Bean
    RedemptionRetrieveService redemptionRetrieveService(final RedemptionStorage redemptionStorage) {
        return new RedemptionRetrieveServiceImpl(redemptionStorage);
    }

    @Bean
    RedemptionValidator redemptionValidator(final VoucherStorage voucherStorage,
                                            final RedemptionStorage redemptionStorage) {
        return new RedemptionValidatorImpl(voucherStorage, redemptionStorage);
    }

    @Bean
    RedemptionCommandService redemptionCommandService(final RedemptionStorage redemptionStorage,
                                                      final IdGenerator idGenerator,
                                                      final Clock clock) {
        return new RedemptionCommandServiceImpl(
                redemptionStorage,
                idGenerator::nextId,
                clock
        );
    }

    @Bean
    RedemptionManagerService redemptionManagerService(final RedemptionRetrieveService redemptionRetrieveService,
                                                      final RedemptionCommandService redemptionCommandService) {
        return new RedemptionManagerServiceImpl(
                redemptionRetrieveService,
                redemptionCommandService
        );
    }
}
