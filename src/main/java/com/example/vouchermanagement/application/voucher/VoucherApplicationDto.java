package com.example.vouchermanagement.application.voucher;

import lombok.With;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record VoucherApplicationDto(
        String id,
        String redemptionType,
        BigDecimal discountAmount,
        Instant validFrom,
        Instant expiredAt,
        Instant createdAt,
        boolean isDeleted,
        @With boolean isRedeemed,
        @Nullable Integer redemptionCount,
        @Nullable Integer limitRedemption
) {
    public VoucherApplicationDto {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
        Objects.requireNonNull(validFrom);
        Objects.requireNonNull(expiredAt);
        Objects.requireNonNull(createdAt);
    }
}
