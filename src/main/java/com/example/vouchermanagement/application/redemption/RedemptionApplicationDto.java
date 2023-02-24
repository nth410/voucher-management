package com.example.vouchermanagement.application.redemption;

import java.time.Instant;

public record RedemptionApplicationDto(
        String id,
        String voucherId,
        Instant redeemAt
) {
    public RedemptionApplicationDto {
    }
}
