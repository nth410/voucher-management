package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.application.voucher.VoucherStorage;
import com.example.vouchermanagement.domain.voucher.specs.HasLimitRedemption;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

record RedemptionValidatorImpl(
        VoucherStorage voucherStorage,
        RedemptionStorage redemptionStorage
) implements RedemptionValidator {

    @Override
    public void throwIfRedeemWithLimit(final String voucherId) {
        final var redemptions = redemptionStorage.retrieveAllByVoucherId(voucherId);
        final var redemptionCount = redemptions.isEmpty() ? 0 : redemptions.size();

        final var voucher = voucherStorage.retrieveById(voucherId);
        final var redemptionType = voucher.redemptionType();
        if (redemptionType == RedemptionType.SINGLE_REDEMPTION || redemptionType == RedemptionType.X_TIMES_REDEMPTION) {
            if (redemptionCount >= ((HasLimitRedemption) voucher).limitRedemption()) {
                throw new RedemptionLimitReachedException("voucher is reached limit");
            }
        }
    }
}
