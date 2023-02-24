package com.example.vouchermanagement.infrastructure.persistence.redemption;

import com.example.vouchermanagement.application.redemption.RedemptionStorage;
import com.example.vouchermanagement.domain.redemption.Redemption;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

record RedemptionSpringDataStorage(
        RedemptionRepository redemptionRepository
) implements RedemptionStorage {
    @Override
    public Redemption store(final Redemption redemption) {
        final var saveRedemption = redemptionRepository.save(toDocument(redemption));
        return fromDocument(saveRedemption);
    }

    @Override
    public List<Redemption> retrieveAllByVoucherId(final String voucherId) {
        final var redemptions = redemptionRepository.findAllByIdVoucherId(voucherId);

        return StreamSupport.stream(redemptions.spliterator(), false)
                .map(RedemptionSpringDataStorage::fromDocument)
                .collect(Collectors.toList());
    }

    static RedemptionDocument toDocument(final Redemption domain) {
        return new RedemptionDocument(
                new RedemptionDocumentId(
                        domain.id(),
                        domain.voucherId()
                ),
                domain.redeemAt()
        );
    }

    static Redemption fromDocument(final RedemptionDocument document) {
        return new Redemption(
                document.id().id(),
                document.id().voucherId(),
                document.redeemAt()
        );
    }
}
