package com.example.vouchermanagement.application.voucher;

import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import com.example.vouchermanagement.domain.voucher.specs.RedemptionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class VoucherCommandServiceImplTest {

    private static final String TEST_VOUCHER_ID = "test-voucher-id";
    private static final String TEST_REDEMPTION_TYPE = "SINGLE_REDEMPTION";
    private static final Instant TEST_INSTANT = Instant.parse("2023-02-16T10:15:30.00Z");

    private VoucherCommandServiceImpl voucherCommandService;

    @Mock
    private VoucherStorage voucherStorage;

    @Mock
    private VoucherValidator voucherValidator;

    @Mock
    private Supplier<String> voucherIdGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        voucherCommandService = new VoucherCommandServiceImpl(voucherStorage, voucherValidator,
                voucherIdGenerator, Clock.fixed(TEST_INSTANT, ZoneId.systemDefault()));
    }

    @Test
    void testCreate() {
        // given
        VoucherApplicationDto voucherApplicationDto = new VoucherApplicationDto(
                TEST_VOUCHER_ID,
                TEST_REDEMPTION_TYPE,
                new BigDecimal(10),
                TEST_INSTANT,
                TEST_INSTANT,
                TEST_INSTANT,
                false,
                false,
                null,
                null
        );

        Voucher expectedVoucher = new SingleRedemptionVoucher(
                TEST_VOUCHER_ID,
                RedemptionType.valueOf(TEST_REDEMPTION_TYPE),
                new BigDecimal(10),
                TEST_INSTANT,
                TEST_INSTANT,
                TEST_INSTANT,
                false,
                false);

        // when
        when(voucherIdGenerator.get()).thenReturn(TEST_VOUCHER_ID);
        doNothing().when(voucherValidator).throwIfVoucherInvalid(any());
        when(voucherStorage.store(expectedVoucher)).thenReturn(expectedVoucher);

        Voucher result = voucherCommandService.create(voucherApplicationDto);

        // then
        verify(voucherIdGenerator).get();
        verify(voucherValidator).throwIfVoucherInvalid(expectedVoucher);
        verify(voucherStorage).store(expectedVoucher);
        verifyNoMoreInteractions(voucherIdGenerator, voucherValidator, voucherStorage);

        assertEquals(expectedVoucher, result);
    }

    @Test
    void testCreateBulk() {
        // given
        VoucherApplicationDto voucherApplicationDto = new VoucherApplicationDto(
                TEST_VOUCHER_ID,
                TEST_REDEMPTION_TYPE,
                new BigDecimal(10),
                TEST_INSTANT,
                TEST_INSTANT,
                TEST_INSTANT,
                false,
                false,
                null,
                null
        );
        List<VoucherApplicationDto> voucherApplicationDtos = Collections.singletonList(voucherApplicationDto);

        Voucher expectedVoucher = new SingleRedemptionVoucher(
                TEST_VOUCHER_ID,
                RedemptionType.valueOf(TEST_REDEMPTION_TYPE),
                new BigDecimal(10),
                TEST_INSTANT,
                TEST_INSTANT,
                TEST_INSTANT,
                false,
                false);
        List<Voucher> expectedVouchers = Collections.singletonList(expectedVoucher);

        // when
        when(voucherIdGenerator.get()).thenReturn(TEST_VOUCHER_ID);
        doNothing().when(voucherValidator).throwIfVoucherInvalid(any());
        when(voucherStorage.storeAll(expectedVouchers)).thenReturn(expectedVouchers);

        // then
        List<Voucher> results = voucherCommandService.createBulk(voucherApplicationDtos);

        verify(voucherIdGenerator).get();
        verify(voucherValidator).throwIfVoucherInvalid(expectedVouchers.get(0));
        verify(voucherStorage).storeAll(expectedVouchers);
        verifyNoMoreInteractions(voucherIdGenerator, voucherValidator, voucherStorage);

        assertEquals(expectedVouchers, results);
    }
}