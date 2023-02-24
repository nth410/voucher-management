package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.application.common.IdGenerator;
import com.example.vouchermanagement.application.redemption.RedemptionCommandService;
import com.example.vouchermanagement.application.redemption.RedemptionRetrieveService;
import com.example.vouchermanagement.application.redemption.RedemptionValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
class VoucherServiceConfig {

    @Bean
    VoucherRetrieveService voucherRetrieveService(final VoucherStorage voucherStorage) {
        return new VoucherRetrieveServiceImpl(voucherStorage);
    }

    @Bean
    VoucherValidator voucherValidator(final VoucherStorage voucherStorage,
                                      final Clock clock) {
        return new VoucherValidatorImpl(voucherStorage, clock);
    }

    @Bean
    VoucherCommandService voucherCommandService(final VoucherStorage voucherStorage,
                                                final VoucherValidator voucherValidator,
                                                final IdGenerator idGenerator,
                                                final Clock clock) {
        return new VoucherCommandServiceImpl(
                voucherStorage,
                voucherValidator,
                () -> idGenerator.nextId(),
                clock
        );
    }

    @Bean
    VoucherManagerService voucherManagerService(final VoucherRetrieveService voucherRetrieveService,
                                                final VoucherCommandService voucherCommandService,
                                                final RedemptionRetrieveService redemptionRetrieveService,
                                                final RedemptionCommandService redemptionCommandService,
                                                final VoucherValidator voucherValidator,
                                                final RedemptionValidator redemptionValidator) {
        return new VoucherManagerServiceImpl(
                voucherRetrieveService,
                voucherCommandService,
                redemptionRetrieveService,
                redemptionCommandService,
                voucherValidator,
                redemptionValidator
        );
    }
}
