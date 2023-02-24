package com.example.vouchermanagement.infrastructure.web.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

import java.util.Objects;

record VoucherResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "redemptionType") String redemptionType,
        @JsonProperty(value = "discountAmount") String discountAmount,
        @JsonProperty(value = "validFrom") String validFrom,
        @JsonProperty(value = "expiredAt") String expiredAt,
        @JsonProperty(value = "createdAt") String createdAt,
        @JsonProperty(value = "isDeleted") boolean isDeleted,
        @JsonProperty(value = "isRedeemed") boolean isRedeemed,
        @JsonProperty(value = "redemptionCount") @Nullable Integer redemptionCount,
        @JsonProperty(value = "limitRedemption") @Nullable Integer limitRedemption
) {
    VoucherResponse {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redemptionType);
        Objects.requireNonNull(discountAmount);
        Objects.requireNonNull(validFrom);
        Objects.requireNonNull(expiredAt);
        Objects.requireNonNull(createdAt);
    }
}
