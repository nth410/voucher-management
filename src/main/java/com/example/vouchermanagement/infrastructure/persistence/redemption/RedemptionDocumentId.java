package com.example.vouchermanagement.infrastructure.persistence.redemption;

import org.springframework.data.annotation.Id;

import java.util.Objects;

record RedemptionDocumentId(
        @Id String id,
        String voucherId
) {
    RedemptionDocumentId {
        Objects.requireNonNull(id);
        Objects.requireNonNull(voucherId);
    }
}
