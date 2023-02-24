package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.redemption.Redemption;
import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherManagerService {
    Voucher create(VoucherApplicationDto voucherApplicationDto);

    List<Voucher> createBulk(List<VoucherApplicationDto> voucherApplicationDtos);

    Voucher update(String id, VoucherApplicationDto voucherApplicationDto);

    Voucher retrieveById(String id);

    List<Voucher> retrieveAll();

    void delete(String id);

    Redemption redeem(String voucherId);
}
