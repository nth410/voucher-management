package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

import java.util.List;

public record RedemptionManagerServiceImpl(
        RedemptionRetrieveService redemptionRetrieveService,
        RedemptionCommandService redemptionCommandService
) implements RedemptionManagerService {
    @Override
    public List<Redemption> retrieveByVoucherId(String voucherId) {
        return redemptionRetrieveService.retrieveByVoucherId(voucherId);
    }
}
