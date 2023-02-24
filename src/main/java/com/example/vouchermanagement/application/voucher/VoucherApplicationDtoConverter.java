package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.MultipleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.XTimesRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.time.Instant;
import java.util.Optional;

class VoucherApplicationDtoConverter {

    private VoucherApplicationDtoConverter() {
        throw new RuntimeException("Utility class constructor invoked");
    }

    static Voucher toVoucher(
            final String voucherId,
            final VoucherApplicationDto voucherApplicationDto,
            final Instant createAt,
            final boolean isRedeemed
    ) {

        final var redemptionType = toRedemptionType(voucherApplicationDto.redemptionType());

        return switch (redemptionType) {
            case SINGLE_REDEMPTION -> toSingleRedemptionVoucher(voucherId, voucherApplicationDto, createAt, isRedeemed);
            case MULTIPLE_REDEMPTION ->
                    toMultipleRedemptionVoucher(voucherId, voucherApplicationDto, createAt, isRedeemed);
            case X_TIMES_REDEMPTION ->
                    toXTimesRedemptionVoucher(voucherId, voucherApplicationDto, createAt, isRedeemed);
            default -> throw new RuntimeException("no such redemption type");
        };
    }

    private static Voucher toSingleRedemptionVoucher(
            final String voucherId,
            final VoucherApplicationDto voucherApplicationDto,
            final Instant createAt,
            final boolean isRedeemed
    ) {
        return new SingleRedemptionVoucher(
                voucherId,
                RedemptionType.SINGLE_REDEMPTION,
                voucherApplicationDto.discountAmount(),
                voucherApplicationDto.validFrom(),
                voucherApplicationDto.expiredAt(),
                createAt,
                voucherApplicationDto.isDeleted(),
                isRedeemed
        );
    }

    private static Voucher toMultipleRedemptionVoucher(
            final String voucherId,
            final VoucherApplicationDto voucherApplicationDto,
            final Instant createAt,
            final boolean isRedeemed
    ) {
        return new MultipleRedemptionVoucher(
                voucherId,
                RedemptionType.MULTIPLE_REDEMPTION,
                voucherApplicationDto.discountAmount(),
                voucherApplicationDto.validFrom(),
                voucherApplicationDto.expiredAt(),
                createAt,
                voucherApplicationDto.isDeleted(),
                isRedeemed,
                voucherApplicationDto.redemptionCount() == null ? 0 : voucherApplicationDto.redemptionCount()
        );
    }

    private static Voucher toXTimesRedemptionVoucher(
            final String voucherId,
            final VoucherApplicationDto voucherApplicationDto,
            final Instant createAt,
            final boolean isRedeemed
    ) {
        return new XTimesRedemptionVoucher(
                voucherId,
                RedemptionType.X_TIMES_REDEMPTION,
                voucherApplicationDto.discountAmount(),
                voucherApplicationDto.validFrom(),
                voucherApplicationDto.expiredAt(),
                createAt,
                voucherApplicationDto.isDeleted(),
                isRedeemed,
                voucherApplicationDto.redemptionCount() == null ? 0 : voucherApplicationDto.redemptionCount(),
                voucherApplicationDto.limitRedemption() == null ? 0 : voucherApplicationDto.limitRedemption()
        );
    }

    private static RedemptionType toRedemptionType(final String redemptionType) {
        return Optional.of(redemptionType)
                .map(RedemptionType::valueOf)
                .orElseThrow(() -> new RuntimeException("no such redemption type"));
    }
}
