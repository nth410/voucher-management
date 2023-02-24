package com.example.vouchermanagement.infrastructure.persistence.redemption;

import org.springframework.data.repository.CrudRepository;

interface RedemptionRepository extends CrudRepository<RedemptionDocument, RedemptionDocumentId> {
    Iterable<RedemptionDocument> findAllByIdVoucherId(String voucherId);
}
