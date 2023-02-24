package com.example.vouchermanagement.domain.voucher;

import com.example.vouchermanagement.domain.voucher.specs.HasLimitRedemption;
import com.example.vouchermanagement.domain.voucher.specs.HasMultipleRedemption;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;
import lombok.With;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public record XTimesRedemptionVoucher(
        String id,
        RedemptionType redemptionType,
        BigDecimal discountAmount,
        Instant validFrom,
        Instant expiredAt,
        Instant createdAt,
        boolean isDeleted,
        boolean isRedeemed,
        @With int redemptionCount,
        int limitRedemption
) implements Voucher, HasMultipleRedemption, HasLimitRedemption {

    public XTimesRedemptionVoucher {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
        Objects.requireNonNull(validFrom);
        Objects.requireNonNull(expiredAt);
        Objects.requireNonNull(createdAt);
    }
}
