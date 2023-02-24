package com.example.vouchermanagement.infrastructure.web.redemption;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public record RedemptionResponse(
        @JsonProperty(value = "id") String id,
        @JsonProperty(value = "voucherId") String voucherId,
        @JsonProperty(value = "redeemAt") String redeemAt

) {
    public RedemptionResponse {
        Objects.requireNonNull(id);
        Objects.requireNonNull(voucherId);
        Objects.requireNonNull(voucherId);
    }
}
