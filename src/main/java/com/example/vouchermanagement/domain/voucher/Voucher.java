package com.example.vouchermanagement.domain.voucher;

import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.math.BigDecimal;
import java.time.Instant;

public interface Voucher {
    String id();

    RedemptionType redemptionType();

    BigDecimal discountAmount();

    Instant validFrom();

    Instant expiredAt();

    Instant createdAt();

    boolean isDeleted();

    boolean isRedeemed();
}
