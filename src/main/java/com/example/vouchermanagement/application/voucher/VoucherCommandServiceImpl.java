package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.time.Clock;
import java.util.List;
import java.util.function.Supplier;

record VoucherCommandServiceImpl(
        VoucherStorage voucherStorage,
        VoucherValidator voucherValidator,
        Supplier<String> voucherIdGenerator,
        Clock clock
) implements VoucherCommandService {
    @Override
    public Voucher create(final VoucherApplicationDto voucherApplicationDto) {
        final var voucherId = voucherIdGenerator.get();
        final var voucher = VoucherApplicationDtoConverter.toVoucher(
                voucherId,
                voucherApplicationDto,
                clock.instant(),
                false);

        voucherValidator.throwIfVoucherInvalid(voucher);

        return voucherStorage.store(voucher);
    }

    @Override
    public List<Voucher> createBulk(final List<VoucherApplicationDto> voucherApplicationDtos) {
        final var vouchers = voucherApplicationDtos
                .stream()
                .map(voucherApplicationDto -> {
                    final var voucherId = voucherIdGenerator.get();
                    final var voucher = VoucherApplicationDtoConverter.toVoucher(
                            voucherId,
                            voucherApplicationDto,
                            clock.instant(),
                            false
                    );

                    voucherValidator.throwIfVoucherInvalid(voucher);
                    return voucher;
                }).toList();

        return voucherStorage.storeAll(vouchers);
    }

    @Override
    public Voucher update(final String id, final VoucherApplicationDto voucherApplicationDto) {
        final var voucher = VoucherApplicationDtoConverter.toVoucher(
                id,
                voucherApplicationDto,
                voucherApplicationDto.createdAt(),
                voucherApplicationDto.isRedeemed()
        );

        voucherValidator.throwIfVoucherInvalid(voucher);
        return voucherStorage.store(voucher);
    }

    @Override
    public void delete(final String id) {
        voucherStorage.delete(id);
    }
}
