package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.example.vouchermanagement.domain.voucher.MultipleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.XTimesRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.util.Optional;

class VoucherFromDocumentMapper {
    static Voucher fromDocument(final VoucherDocument document) {
        final var redemptionType = RedemptionType.valueOf(document.redemptionType());

        return switch (redemptionType) {
            case SINGLE_REDEMPTION -> toSingleRedemptionVoucher(document);
            case MULTIPLE_REDEMPTION -> toMultipleRedemptionVoucher(document);
            case X_TIMES_REDEMPTION -> toXTimesRedemptionVoucher(document);
            default -> throw new RuntimeException("There is no redemption type");
        };
    }

    private static Voucher toSingleRedemptionVoucher(final VoucherDocument document) {
        return new SingleRedemptionVoucher(
                document.id(),
                RedemptionType.valueOf(document.redemptionType()),
                document.discountAmount(),
                document.validFrom(),
                document.expiredAt(),
                document.createdAt(),
                document.isDeleted(),
                document.isRedeemed()
        );
    }

    private static Voucher toMultipleRedemptionVoucher(final VoucherDocument document) {
        return new MultipleRedemptionVoucher(
                document.id(),
                RedemptionType.valueOf(document.redemptionType()),
                document.discountAmount(),
                document.validFrom(),
                document.expiredAt(),
                document.createdAt(),
                document.isDeleted(),
                document.isRedeemed(),
                Optional.ofNullable(document.redemptionCount()).orElse(0)
        );
    }

    private static Voucher toXTimesRedemptionVoucher(final VoucherDocument document) {
        return new XTimesRedemptionVoucher(
                document.id(),
                RedemptionType.valueOf(document.redemptionType()),
                document.discountAmount(),
                document.validFrom(),
                document.expiredAt(),
                document.createdAt(),
                document.isDeleted(),
                document.isRedeemed(),
                Optional.ofNullable(document.redemptionCount()).orElse(0),
                Optional.ofNullable(document.limitRedemption()).orElse(0)
        );
    }
}
