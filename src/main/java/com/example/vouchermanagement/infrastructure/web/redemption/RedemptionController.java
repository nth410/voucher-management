package com.example.vouchermanagement.infrastructure.web.redemption;

import com.example.vouchermanagement.application.redemption.RedemptionManagerService;
import com.example.vouchermanagement.domain.redemption.Redemption;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/vouchers/{voucher_id}/redemptions")
class RedemptionController {

    private final RedemptionManagerService redemptionManagerService;

    RedemptionController(final RedemptionManagerService redemptionManagerService) {
        this.redemptionManagerService = Objects.requireNonNull(redemptionManagerService);
    }

    @GetMapping
    List<RedemptionResponse> retrieveAll(
            @PathVariable("voucher_id") String voucherId
    ) {
        final var redemptions = redemptionManagerService.retrieveByVoucherId(voucherId)
                .stream()
                .map(RedemptionController::toResponse)
                .toList();

        return redemptions;
    }

    private static RedemptionResponse toResponse(final Redemption redemption) {
        return new RedemptionResponse(
                redemption.id(),
                redemption.voucherId(),
                redemption.redeemAt().toString()
        );
    }
}
