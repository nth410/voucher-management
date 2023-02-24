package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

import java.util.List;

public interface RedemptionStorage {
    Redemption store(Redemption redemption);

    List<Redemption> retrieveAllByVoucherId(String voucherId);
}
