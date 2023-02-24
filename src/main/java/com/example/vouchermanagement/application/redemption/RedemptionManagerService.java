package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

import java.util.List;

public interface RedemptionManagerService {
    Redemption save(RedemptionApplicationDto redemptionApplicationDto);

    List<Redemption> retrieveByVoucherId(String voucherId);
}
