package com.example.vouchermanagement.infrastructure.persistence.voucher;

import com.example.vouchermanagement.application.voucher.VoucherStorage;
import com.example.vouchermanagement.application.voucher.VoucherValidator;
import com.example.vouchermanagement.domain.voucher.Voucher;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

record VoucherSpringDataStorage(
        VoucherRepository voucherRepository
) implements VoucherStorage {

    VoucherSpringDataStorage {
        Objects.requireNonNull(voucherRepository);
    }

    @Override
    public Voucher store(final Voucher voucher) {
        var saveVoucher = voucherRepository.save(VoucherToDocumentMapper.toDocument(voucher));
        return VoucherFromDocumentMapper.fromDocument(saveVoucher);
    }

    @Override
    public List<Voucher> storeAll(final List<Voucher> vouchers) {
        final var voucherDocuments = vouchers
                .stream()
                .map(VoucherToDocumentMapper::toDocument)
                .toList();
        final var saveVouchers = voucherRepository.saveAll(voucherDocuments);

        return convertIterableToList(saveVouchers);
    }

    @Override
    public Voucher retrieveById(final String id) {
        return voucherRepository.findById(id)
                .map(VoucherFromDocumentMapper::fromDocument)
                .orElseThrow(() -> new VoucherValidator.NotFoundVoucherException("resource not found"));
    }

    @Override
    public List<Voucher> retrieveAll() {
        final var vouchers = voucherRepository.findAll();

        return convertIterableToList(vouchers);
    }

    @Override
    public List<Voucher> retrieveAllByRedemptionType(final String redemptionType) {
        final var vouchers = voucherRepository.findAllByRedemptionType(redemptionType);

        return convertIterableToList(vouchers);
    }

    @Override
    public void delete(final String id) {
        voucherRepository.deleteById(id);
    }

    private List<Voucher> convertIterableToList(final Iterable<VoucherDocument> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                .map(VoucherFromDocumentMapper::fromDocument)
                .collect(Collectors.toList());
    }
}
