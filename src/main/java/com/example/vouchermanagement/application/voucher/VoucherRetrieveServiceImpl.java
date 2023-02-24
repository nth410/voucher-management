package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

record VoucherRetrieveServiceImpl(
        VoucherStorage voucherStorage
) implements VoucherRetrieveService {

    @Override
    public Voucher retrieveById(final String id) {
        return voucherStorage.retrieveById(id);
    }

    @Override
    public List<Voucher> retrieveAll() {
        return voucherStorage.retrieveAll();
    }
}
