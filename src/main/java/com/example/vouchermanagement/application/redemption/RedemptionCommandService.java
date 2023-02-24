package com.example.vouchermanagement.application.redemption;

import com.example.vouchermanagement.domain.redemption.Redemption;

public interface RedemptionCommandService {
    Redemption save(RedemptionApplicationDto redemptionApplicationDto);
}
