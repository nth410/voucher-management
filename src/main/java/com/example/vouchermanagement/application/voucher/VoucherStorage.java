package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherStorage {
    Voucher store(Voucher voucher);

    List<Voucher> storeAll(List<Voucher> vouchers);

    Voucher retrieveById(String id);

    List<Voucher> retrieveAll();

    List<Voucher> retrieveAllByRedemptionType(String redemptionType);

    void delete(String id);
}
