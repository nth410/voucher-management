package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

import java.util.List;

record RedemptionRetrieveServiceImpl(
        RedemptionStorage redemptionStorage
) implements RedemptionRetrieveService {

    @Override
    public List<Redemption> retrieveByVoucherId(final String voucherId) {
        return redemptionStorage.retrieveAllByVoucherId(voucherId);
    }
}
