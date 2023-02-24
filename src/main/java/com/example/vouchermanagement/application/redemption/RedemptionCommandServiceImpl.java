package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

import java.time.Clock;
import java.util.function.Supplier;

record RedemptionCommandServiceImpl(
        RedemptionStorage redemptionStorage,
        Supplier<String> redemptionIdGenerator,
        Clock clock
) implements RedemptionCommandService {

    @Override
    public Redemption save(final RedemptionApplicationDto redemptionApplicationDto) {
        final var redemption = new Redemption(
                redemptionIdGenerator.get(),
                redemptionApplicationDto.voucherId(),
                clock.instant()
        );

        return redemptionStorage.store(redemption);
    }
}
