package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;

public interface VoucherRetrieveService {
    Voucher retrieveById(String id);

    List<Voucher> retrieveAll();
}
