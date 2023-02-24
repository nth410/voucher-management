package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherCommandService {
    Voucher create(VoucherApplicationDto voucherApplicationDto);

    List<Voucher> createBulk(List<VoucherApplicationDto> voucherApplicationDtos);

    Voucher update(String id, VoucherApplicationDto voucherApplicationDto);

    void delete(String id);
}
