package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.mongodb.lang.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Document("vouchers")
record VoucherDocument(
        @Id String id,
        String redemptionType,
        BigDecimal discountAmount,
        Instant validFrom,
        Instant expiredAt,
        Instant createdAt,
        boolean isDeleted,
        boolean isRedeemed,
        @Nullable Integer redemptionCount,
        @Nullable Integer limitRedemption
) {
    VoucherDocument {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
        Objects.requireNonNull(validFrom);
        Objects.requireNonNull(expiredAt);
        Objects.requireNonNull(createdAt);
    }
}
