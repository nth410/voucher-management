package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.application.redemption.RedemptionApplicationDto;
import com.example.vouchermanagement.application.redemption.RedemptionCommandService;
import com.example.vouchermanagement.application.redemption.RedemptionRetrieveService;
import com.example.vouchermanagement.application.redemption.RedemptionValidator;
import com.example.vouchermanagement.domain.redemption.Redemption;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VoucherManagerServiceImplTest {
    @Mock
    private VoucherRetrieveService voucherRetrieveService;

    @Mock
    private VoucherCommandService voucherCommandService;

    @Mock
    private RedemptionRetrieveService redemptionRetrieveService;

    @Mock
    private RedemptionCommandService redemptionCommandService;

    @Mock
    private VoucherValidator voucherValidator;

    @Mock
    private RedemptionValidator redemptionValidator;

    private VoucherManagerServiceImpl voucherManagerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherManagerService = new VoucherManagerServiceImpl(
                voucherRetrieveService,
                voucherCommandService,
                redemptionRetrieveService,
                redemptionCommandService,
                voucherValidator,
                redemptionValidator
        );
    }

    @Test
    public void givenVoucherId_whenRedeem_thenReturnRedemption() {
        // given
        String voucherId = "123456";
        Voucher voucher = new SingleRedemptionVoucher(
                voucherId,
                RedemptionType.SINGLE_REDEMPTION,
                new BigDecimal("10.00"),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                false,
                false,
                1
        );

        // when
        when(voucherRetrieveService.retrieveById(voucherId)).thenReturn(voucher);
        List<Redemption> redemptions = new ArrayList<>();
        Redemption redemption = new Redemption(
                "1",
                voucherId,
                Instant.ofEpochMilli(1677238315407L)
        );
        redemptions.add(redemption);
        when(redemptionRetrieveService.retrieveByVoucherId(voucherId)).thenReturn(redemptions);
        when(redemptionCommandService.save(any(RedemptionApplicationDto.class))).thenReturn(redemption);
        when(voucherCommandService.update(anyString(), any(VoucherApplicationDto.class))).thenReturn(voucher);

        // then
        Redemption result = voucherManagerService.redeem(voucherId);

        verify(voucherValidator, times(1)).throwIfNotFoundVoucher(voucherId);
        verify(voucherValidator, times(1)).throwIfVoucherOutOfDate(voucherId);
        verify(redemptionValidator, times(1)).throwIfRedeemWithLimit(voucherId);
        verify(voucherRetrieveService, times(1)).retrieveById(voucherId);
        verify(redemptionRetrieveService, times(1)).retrieveByVoucherId(voucherId);
        verify(redemptionCommandService, times(1)).save(any(RedemptionApplicationDto.class));
        verify(voucherCommandService, times(1)).update(anyString(), any(VoucherApplicationDto.class));
        assertEquals(result, redemption);
    }
}
