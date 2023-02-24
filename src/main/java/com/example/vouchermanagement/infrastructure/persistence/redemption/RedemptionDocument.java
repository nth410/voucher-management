package com.example.vouchermanagement.infrastructure.persistence.redemption;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Objects;

@Document("redemptions")
record RedemptionDocument(
        @Id RedemptionDocumentId id,
        Instant redeemAt
) {
    RedemptionDocument {
        Objects.requireNonNull(id);
        Objects.requireNonNull(redeemAt);
    }
}
