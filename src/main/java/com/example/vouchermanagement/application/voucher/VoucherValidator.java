package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.io.Serial;

public interface VoucherValidator {

    void throwIfVoucherInvalid(Voucher voucher);

    Voucher throwIfNotFoundVoucher(String voucherId);

    void throwIfVoucherOutOfDate(String voucherId);

    class InvalidVoucherException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 8025320747228183131L;

        public InvalidVoucherException(String message) {
            super(message);
        }
    }

    class NotFoundVoucherException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 5692356867593635525L;

        public NotFoundVoucherException(String message) {
            super(message);
        }
    }

    class VoucherOutOfDateException extends RuntimeException {
        @Serial
        private static final long serialVersionUID = 1882128954163577148L;

        public VoucherOutOfDateException(String message) {
            super(message);
        }
    }
}
