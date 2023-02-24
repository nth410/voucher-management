package com.example.vouchermanagement.domain.redemption;

import java.time.Instant;
import java.util.Objects;

public record Redemption(
        String id,
        String voucherId,
        Instant redeemAt
) {
    public Redemption {
        Objects.requireNonNull(id);
        Objects.requireNonNull(voucherId);
        Objects.requireNonNull(redeemAt);
    }
}
