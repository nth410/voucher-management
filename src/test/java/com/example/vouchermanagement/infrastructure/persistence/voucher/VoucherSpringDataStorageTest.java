package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.example.vouchermanagement.application.voucher.VoucherStorage;
import com.example.vouchermanagement.domain.voucher.SingleRedemptionVoucher;
import com.example.vouchermanagement.domain.voucher.Voucher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static com.example.vouchermanagement.domain.voucher.specs.RedemptionType.SINGLE_REDEMPTION;

@ExtendWith(MockitoExtension.class)
class VoucherSpringDataStorageTest {

    private VoucherStorage voucherStorage;

    @Mock
    private VoucherRepository voucherRepository;

    @BeforeEach
    public void setup() {
        voucherStorage = new VoucherSpringDataStorage(voucherRepository);
    }

    @Test
    @DisplayName("Should store a single voucher successfully")
    public void testStoreVoucher() {
        Voucher voucher = new SingleRedemptionVoucher(
                "1",
                SINGLE_REDEMPTION,
                new BigDecimal(10),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                false,
                false
        );


        VoucherDocument voucherDocument = new VoucherDocument(
                "1",
                "SINGLE_REDEMPTION",
                new BigDecimal(10),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                false,
                false,
                null,
                null
        );

        Mockito.when(voucherRepository.save(voucherDocument)).thenReturn(voucherDocument);

        Voucher storedVoucher = voucherStorage.store(voucher);

        Assertions.assertEquals(voucher, storedVoucher);
    }

    @Test
    @DisplayName("Should retrieve a voucher by ID successfully")
    public void testRetrieveVoucherById() {
        VoucherDocument voucherDocument = new VoucherDocument(
                "1",
                "SINGLE_REDEMPTION",
                new BigDecimal(10),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                Instant.ofEpochMilli(1677238315407L),
                false,
                false,
                null,
                null
        );

        Mockito.when(voucherRepository.findById("1")).thenReturn(java.util.Optional.of(voucherDocument));

        Voucher retrievedVoucher = voucherStorage.retrieveById("1");

        Assertions.assertNotNull(retrievedVoucher);
        Assertions.assertEquals(voucherDocument.id(), retrievedVoucher.id());
        Assertions.assertEquals(voucherDocument.redemptionType(), retrievedVoucher.redemptionType().name());
        Assertions.assertEquals(voucherDocument.discountAmount(), retrievedVoucher.discountAmount());
        Assertions.assertEquals(voucherDocument.validFrom(), retrievedVoucher.validFrom());
    }
}