package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.application.redemption.RedemptionApplicationDto;
import com.example.vouchermanagement.application.redemption.RedemptionCommandService;
import com.example.vouchermanagement.application.redemption.RedemptionRetrieveService;
import com.example.vouchermanagement.application.redemption.RedemptionValidator;
import com.example.vouchermanagement.domain.redemption.Redemption;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.specs.HasLimitRedemption;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;

import java.util.List;

public record VoucherManagerServiceImpl(
        VoucherRetrieveService voucherRetrieveService,
        VoucherCommandService voucherCommandService,
        RedemptionRetrieveService redemptionRetrieveService,
        RedemptionCommandService redemptionCommandService,
        VoucherValidator voucherValidator,
        RedemptionValidator redemptionValidator
) implements VoucherManagerService {

    @Override
    public Voucher create(final VoucherApplicationDto voucherApplicationDto) {
        return voucherCommandService.create(voucherApplicationDto);
    }

    @Override
    public List<Voucher> createBulk(final List<VoucherApplicationDto> voucherApplicationDtos) {
        return voucherCommandService.createBulk(voucherApplicationDtos);
    }

    @Override
    public Voucher update(final String id,
                          final VoucherApplicationDto voucherApplicationDto
    ) {
        final var voucher = voucherValidator.throwIfNotFoundVoucher(id);
        final var updateVoucherApplicationDto = voucherApplicationDto.withRedeemed(voucher.isRedeemed());
        return voucherCommandService.update(id, updateVoucherApplicationDto);
    }

    @Override
    public Voucher retrieveById(final String id) {
        return voucherRetrieveService.retrieveById(id);
    }

    @Override
    public List<Voucher> retrieveAll() {
        return voucherRetrieveService.retrieveAll();
    }

    @Override
    public void delete(final String id) {
        voucherCommandService.delete(id);
    }

    @Override
    public Redemption redeem(final String voucherId) {
        voucherValidator.throwIfNotFoundVoucher(voucherId);
        voucherValidator.throwIfVoucherOutOfDate(voucherId);
        redemptionValidator.throwIfRedeemWithLimit(voucherId);

        final var voucher = voucherRetrieveService.retrieveById(voucherId);
        final var redemptions = redemptionRetrieveService.retrieveByVoucherId(voucherId);
        final var newRedemptionCount = redemptions == null || redemptions.isEmpty() ? 1 : redemptions.size() + 1;
        // save redeem log to redemptions collection
        final var redemption = new RedemptionApplicationDto(
                null,
                voucher.id(),
                null

        );
        final var saveRedemption = redemptionCommandService.save(redemption);

        final var redemptionType = voucher.redemptionType();
        var isRedeemed = false;
        if (redemptionType == RedemptionType.SINGLE_REDEMPTION || redemptionType == RedemptionType.X_TIMES_REDEMPTION) {
            isRedeemed = ((HasLimitRedemption) voucher).limitRedemption() <= newRedemptionCount;
        } else {
            isRedeemed = true;
        }

        final var updateVoucher = new VoucherApplicationDto(
                voucher.id(),
                redemptionType.name(),
                voucher.discountAmount(),
                voucher.validFrom(),
                voucher.expiredAt(),
                voucher.createdAt(),
                voucher.isDeleted(),
                isRedeemed,
                redemptionType != RedemptionType.SINGLE_REDEMPTION ? newRedemptionCount : null,
                redemptionType == RedemptionType.X_TIMES_REDEMPTION
                        ? ((HasLimitRedemption) voucher).limitRedemption() : null
        );

        voucherCommandService.update(voucher.id(), updateVoucher);

        return saveRedemption;
    }
}
