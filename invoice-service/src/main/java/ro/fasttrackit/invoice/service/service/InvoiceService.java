package ro.fasttrackit.invoice.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs13homework.filters.InvoiceFilters;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;
import ro.fasttrackit.invoice.service.repository.InvoiceDao;
import ro.fasttrackit.invoice.service.repository.InvoiceRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceDao dao;
    private final InvoiceRepository repository;

    public List<InvoiceEntity> findAllInvoices(InvoiceFilters filters) {
        return unmodifiableList(dao.findAllInvoices(filters));
    }

    public Optional<InvoiceEntity> findInvoice(String invoiceId) {
        return repository.findById(invoiceId);
    }

    public InvoiceEntity addInvoice(InvoiceEntity newInvoice) {
        newInvoice.setId(null);
        return repository.save(newInvoice);
    }

    public Optional<InvoiceEntity> deleteInvoice(String invoiceId) {
        Optional<InvoiceEntity> invoiceToDelete = repository.findById(invoiceId);
        invoiceToDelete.ifPresent(this::deleteExistingInvoice);
        return invoiceToDelete;
    }

    private void deleteExistingInvoice(InvoiceEntity invoiceEntity) {
        log.info("Deleting invoice: " + invoiceEntity);
        repository.delete(invoiceEntity);
        //notify with rabbit?
    }
}
