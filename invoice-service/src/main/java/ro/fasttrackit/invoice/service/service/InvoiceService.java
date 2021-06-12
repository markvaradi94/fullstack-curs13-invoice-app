package ro.fasttrackit.invoice.service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs13homework.exceptions.ResourceNotFoundException;
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
    private final ObjectMapper mapper;
    private final InvoiceRepository repository;
    private final InvoiceNotificationsService notificationsService;

    public List<InvoiceEntity> findAllInvoices(InvoiceFilters filters) {
        return unmodifiableList(dao.findAllInvoices(filters));
    }

    public Optional<InvoiceEntity> findInvoice(String invoiceId) {
        return repository.findById(invoiceId);
    }

    public InvoiceEntity addInvoice(InvoiceEntity newInvoice) {
        newInvoice.setId(null);
        InvoiceEntity dbInvoice = repository.save(newInvoice);
        notificationsService.notifyInvoiceCreated(dbInvoice);
        return dbInvoice;
    }

    public Optional<InvoiceEntity> deleteInvoice(String invoiceId) {
        Optional<InvoiceEntity> invoiceToDelete = repository.findById(invoiceId);
        invoiceToDelete.ifPresent(this::deleteExistingInvoice);
        return invoiceToDelete;
    }

    public InvoiceEntity setInvoiceToPaid(String invoiceId) {
        InvoiceEntity dbInvoice = getOrThrow(invoiceId);
        dbInvoice.setPayed(true);
        return repository.save(dbInvoice);
    }

    @SneakyThrows
    public InvoiceEntity patchInvoice(String invoiceId, JsonPatch patch) {
        InvoiceEntity dbInvoice = getOrThrow(invoiceId);
        JsonNode patchedInvoiceJson = patch.apply(mapper.valueToTree(dbInvoice));
        InvoiceEntity patchedInvoice = mapper.treeToValue(patchedInvoiceJson, InvoiceEntity.class);
        copyInvoice(patchedInvoice, dbInvoice);
        return repository.save(dbInvoice);
    }

    private void copyInvoice(InvoiceEntity newInvoice, InvoiceEntity dbInvoice) {
        dbInvoice.setPayed(newInvoice.getPayed());
    }

    private void deleteExistingInvoice(InvoiceEntity invoiceEntity) {
        log.info("Deleting invoice: " + invoiceEntity);
        repository.delete(invoiceEntity);
    }

    private InvoiceEntity getOrThrow(String invoiceId) {
        return repository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find invoice with id " + invoiceId));
    }
}
