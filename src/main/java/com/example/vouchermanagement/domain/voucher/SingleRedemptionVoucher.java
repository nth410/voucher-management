package com.example.vouchermanagement.domain.voucher;

import com.example.vouchermanagement.domain.voucher.specs.HasLimitRedemption;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.math.BigDecimal;
import java.time.Instant;

public record SingleRedemptionVoucher(
        String id,
        RedemptionType redemptionType,
        BigDecimal discountAmount,
        Instant validFrom,
        Instant expiredAt,
        Instant createdAt,
        boolean isDeleted,
        boolean isRedeemed,
        int limitRedemption
) implements Voucher, HasLimitRedemption {

    public SingleRedemptionVoucher(
            String id,
            RedemptionType redemptionType,
            BigDecimal discountAmount,
            Instant validFrom,
            Instant expiredAt,
            Instant createdAt,
            boolean isDeleted,
            boolean isRedeemed) {
        this(
                id,
                redemptionType,
                discountAmount,
                validFrom,
                expiredAt,
                createdAt,
                isDeleted,
                isRedeemed,
                1
        );
    }
}
