package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.example.vouchermanagement.domain.voucher.MultipleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.XTimesRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.util.Optional;

class VoucherToDocumentMapper {
    static VoucherDocument toDocument(final Voucher domain) {
        return switch (domain.redemptionType()) {
            case SINGLE_REDEMPTION -> fromSingleRedemptionVoucher((SingleRedemptionVoucher) domain);
            case MULTIPLE_REDEMPTION -> fromMultipleRedemptionVoucher((MultipleRedemptionVoucher) domain);
            case X_TIMES_REDEMPTION -> fromXTimesRedemptionVoucher((XTimesRedemptionVoucher) domain);
            default -> throw new RuntimeException("There is no voucher redemption type");
        };
    }

    private static VoucherDocument fromSingleRedemptionVoucher(final SingleRedemptionVoucher domain) {
        return new VoucherDocument(
                domain.id(),
                Optional.ofNullable(domain.redemptionType())
                        .map(RedemptionType::name)
                        .orElseThrow(),
                domain.discountAmount(),
                domain.validFrom(),
                domain.expiredAt(),
                domain.createdAt(),
                domain.isDeleted(),
                domain.isRedeemed(),
                null,
                null
        );
    }

    private static VoucherDocument fromMultipleRedemptionVoucher(final MultipleRedemptionVoucher domain) {
        return new VoucherDocument(
                domain.id(),
                Optional.ofNullable(domain.redemptionType())
                        .map(RedemptionType::name)
                        .orElseThrow(),
                domain.discountAmount(),
                domain.validFrom(),
                domain.expiredAt(),
                domain.createdAt(),
                domain.isDeleted(),
                domain.isRedeemed(),
                domain.redemptionCount(),
                null
        );
    }

    private static VoucherDocument fromXTimesRedemptionVoucher(final XTimesRedemptionVoucher domain) {
        return new VoucherDocument(
                domain.id(),
                Optional.ofNullable(domain.redemptionType())
                        .map(RedemptionType::name)
                        .orElseThrow(),
                domain.discountAmount(),
                domain.validFrom(),
                domain.expiredAt(),
                domain.createdAt(),
                domain.isDeleted(),
                domain.isRedeemed(),
                domain.redemptionCount(),
                domain.limitRedemption()
        );
    }
}
