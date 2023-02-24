package com.example.vouchermanagement.infrastructure.web.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

record VoucherSaveRequest(
        @JsonProperty(value = "redemptionType", required = true) String redemptionType,
        @JsonProperty(value = "discountAmount", required = true) String discountAmount,
        @JsonProperty(value = "validFrom", required = true) long validFromMillis,
        @JsonProperty(value = "expiredAt", required = true) long expiredAtMillis,
        @JsonProperty(value = "redemptionCount") Integer redemptionCount,
        @JsonProperty(value = "limitRedemption") Integer limitRedemption
) {
    VoucherSaveRequest {
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
    }
}
