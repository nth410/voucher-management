package com.example.vouchermanagement.infrastructure.persistence.voucher;

import org.springframework.data.repository.CrudRepository;

interface VoucherRepository extends CrudRepository<VoucherDocument, String> {
    Iterable<VoucherDocument> findAllByRedemptionType(String redemptionType);
}
