package com.example.vouchermanagement.infrastructure.web.voucher;

import com.example.vouchermanagement.application.redemption.RedemptionValidator;
import com.example.vouchermanagement.application.voucher.VoucherApplicationDto;
import com.example.vouchermanagement.application.voucher.VoucherManagerService;
import com.example.vouchermanagement.application.voucher.VoucherValidator;
import com.example.vouchermanagement.domain.voucher.MultipleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.XTimesRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;
import com.example.vouchermanagement.infrastructure.web.redemption.RedemptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/vouchers")
class VoucherController {

    private final VoucherManagerService voucherManagerService;

    VoucherController(final VoucherManagerService voucherManagerService) {
        this.voucherManagerService = Objects.requireNonNull(voucherManagerService);
    }

    @GetMapping
    List<VoucherResponse> retrieveAll() {
        final var vouchers = voucherManagerService.retrieveAll()
                .stream()
                .map(VoucherController::toResponse)
                .toList();

        return vouchers;
    }

    @GetMapping("/{id}")
    VoucherResponse retrieveById(@PathVariable("id") final String id) {
        final var voucher = voucherManagerService.retrieveById(id);
        return toResponse(voucher);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VoucherResponse create(@RequestBody final VoucherSaveRequest request) {
        return toResponse(voucherManagerService.create(toApplicationDto(request)));
    }

    @PostMapping(params = "bulk")
    @ResponseStatus(HttpStatus.CREATED)
    List<VoucherResponse> createBulk(@RequestBody final List<VoucherSaveRequest> request) {
        final var vouchers = request
                .stream()
                .map(VoucherController::toApplicationDto)
                .toList();

        return voucherManagerService.createBulk(vouchers)
                .stream()
                .map(VoucherController::toResponse)
                .toList();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    VoucherResponse update(@PathVariable("id") final String id,
                           @RequestBody final VoucherSaveRequest request) {
        return toResponse(voucherManagerService.update(id, toApplicationDto(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") final String id) {
        voucherManagerService.delete(id);
    }

    @PostMapping("/{id}/redeem")
    RedemptionResponse redeem(@PathVariable("id") final String voucherId) {
        final var redemption = voucherManagerService.redeem(voucherId);
        return new RedemptionResponse(redemption.id(), redemption.voucherId(), redemption.redeemAt().toString());
    }

    private static VoucherResponse toResponse(final Voucher voucher) {
        final var redemptionType = voucher.redemptionType();

        return switch (redemptionType) {
            case SINGLE_REDEMPTION -> fromSingleRedemptionVoucherToResponse((SingleRedemptionVoucher) voucher);
            case MULTIPLE_REDEMPTION -> fromMultipleRedemptionVoucherToResponse((MultipleRedemptionVoucher) voucher);
            case X_TIMES_REDEMPTION -> fromXTimesRedemptionVoucherToResponse((XTimesRedemptionVoucher) voucher);
            default -> throw new RuntimeException("no such redemption type");
        };
    }

    private static VoucherResponse fromSingleRedemptionVoucherToResponse(final SingleRedemptionVoucher domain) {
        return new VoucherResponse(
                domain.id(),
                domain.redemptionType().name(),
                domain.discountAmount().toString(),
                domain.validFrom().toString(),
                domain.expiredAt().toString(),
                domain.createdAt().toString(),
                domain.isDeleted(),
                domain.isRedeemed(),
                null,
                null
        );
    }

    private static VoucherResponse fromMultipleRedemptionVoucherToResponse(final MultipleRedemptionVoucher domain) {
        return new VoucherResponse(
                domain.id(),
                domain.redemptionType().name(),
                domain.discountAmount().toString(),
                domain.validFrom().toString(),
                domain.expiredAt().toString(),
                domain.createdAt().toString(),
                domain.isDeleted(),
                domain.isRedeemed(),
                domain.redemptionCount(),
                null
        );
    }

    private static VoucherResponse fromXTimesRedemptionVoucherToResponse(final XTimesRedemptionVoucher domain) {
        return new VoucherResponse(
                domain.id(),
                domain.redemptionType().name(),
                domain.discountAmount().toString(),
                domain.validFrom().toString(),
                domain.expiredAt().toString(),
                domain.createdAt().toString(),
                domain.isDeleted(),
                domain.isRedeemed(),
                domain.redemptionCount(),
                domain.limitRedemption()
        );
    }

    static VoucherApplicationDto toApplicationDto(VoucherSaveRequest request) {
        final var redemptionType = Optional.of(request.redemptionType()).map(RedemptionType::valueOf).orElseThrow();

        return switch (redemptionType) {
            case SINGLE_REDEMPTION -> fromSingleRedemptionVoucher(request);
            case MULTIPLE_REDEMPTION -> fromMultipleRedemptionVoucher(request);
            case X_TIMES_REDEMPTION -> fromXTimesRedemptionVoucher(request);
            default -> throw new RuntimeException("no such redemption type");
        };
    }

    private static VoucherApplicationDto fromSingleRedemptionVoucher(VoucherSaveRequest voucher) {
        return new VoucherApplicationDto(
                "",
                voucher.redemptionType(),
                new BigDecimal(voucher.discountAmount()),
                Instant.ofEpochMilli(voucher.validFromMillis()),
                Instant.ofEpochMilli(voucher.expiredAtMillis()),
                Instant.now(),
                false,
                false,
                null,
                null
        );
    }

    private static VoucherApplicationDto fromMultipleRedemptionVoucher(VoucherSaveRequest voucher) {
        return new VoucherApplicationDto(
                "",
                voucher.redemptionType(),
                new BigDecimal(voucher.discountAmount()),
                Instant.ofEpochMilli(voucher.validFromMillis()),
                Instant.ofEpochMilli(voucher.expiredAtMillis()),
                Instant.now(),
                false,
                false,
                0,
                null
        );
    }

    private static VoucherApplicationDto fromXTimesRedemptionVoucher(VoucherSaveRequest voucher) {
        return new VoucherApplicationDto(
                "",
                voucher.redemptionType(),
                new BigDecimal(voucher.discountAmount()),
                Instant.ofEpochMilli(voucher.validFromMillis()),
                Instant.ofEpochMilli(voucher.expiredAtMillis()),
                Instant.now(),
                false,
                false,
                0,
                voucher.limitRedemption()
        );
    }

    @ExceptionHandler(VoucherValidator.InvalidVoucherException.class)
    ResponseEntity<Void> handleInvalidVoucherException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(VoucherValidator.NotFoundVoucherException.class)
    ResponseEntity<Void> handleNotFoundVoucherException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(VoucherValidator.VoucherOutOfDateException.class)
    ResponseEntity<Void> handleVoucherOutOfDateException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(RedemptionValidator.RedemptionLimitReachedException.class)
    ResponseEntity<Void> handleRedemptionLimitReachedException() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
