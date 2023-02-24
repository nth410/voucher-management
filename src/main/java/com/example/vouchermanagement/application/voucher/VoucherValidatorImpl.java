package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.math.BigDecimal;
import java.time.Clock;

record VoucherValidatorImpl(
        VoucherStorage voucherStorage,
        Clock clock
) implements VoucherValidator {
    @Override
    public void throwIfVoucherInvalid(final Voucher voucher) {
        // validate date
        if (voucher.validFrom().isAfter(voucher.expiredAt())) {
            throw new InvalidVoucherException("valid from date must be before expired date");
        }
        // validate discount amount
        if (voucher.discountAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidVoucherException("discount amount must be higher than zero");
        }
    }

    @Override
    public Voucher throwIfNotFoundVoucher(final String voucherId) {
        final var voucher = voucherStorage.retrieveById(voucherId);
        if (voucher == null || voucher.isDeleted()) {
            throw new NotFoundVoucherException("voucher " + voucherId + " is not found");
        }
        return voucher;
    }

    @Override
    public void throwIfVoucherOutOfDate(final String voucherId) {
        final var voucher = voucherStorage.retrieveById(voucherId);
        final var instant = clock.instant();
        if (instant.isBefore(voucher.validFrom())) {
            throw new VoucherOutOfDateException("voucher is not active yet");
        }

        if (instant.isAfter(voucher.expiredAt())) {
            throw new VoucherOutOfDateException("voucher is expired");
        }
    }
}
