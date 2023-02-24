package com.example.vouchermanagement.domain.voucher;

import com.example.vouchermanagement.domain.voucher.specs.HasMultipleRedemption;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record MultipleRedemptionVoucher(
        String id,
        RedemptionType redemptionType,
        BigDecimal discountAmount,
        Instant validFrom,
        Instant expiredAt,
        Instant createdAt,
        boolean isDeleted,
        boolean isRedeemed,
        @With int redemptionCount
) implements Voucher, HasMultipleRedemption {

    public MultipleRedemptionVoucher {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
        Objects.requireNonNull(validFrom);
        Objects.requireNonNull(expiredAt);
        Objects.requireNonNull(createdAt);
    }
}
