package com.example.vouchermanagement.application.redemption;

import java.io.Serial;

public interface RedemptionValidator {

    void throwIfRedeemWithLimit(String voucherId);

    class RedemptionLimitReachedException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = -6776862887757093701L;

        public RedemptionLimitReachedException(String message) {
            super(message);
        }
    }
}
