package ro.fasttrackit.invoice.service.controller;

import dto.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.curs13homework.filters.InvoiceFilters;
import ro.fasttrackit.invoice.service.model.mappers.InvoiceMapper;
import ro.fasttrackit.invoice.service.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;
    private final InvoiceMapper mapper;

    @GetMapping
    List<Invoice> getAllInvoices(InvoiceFilters filters) {
        return mapper.toApi(service.findAllInvoices(filters));
    }

    @GetMapping("{invoiceId}")
    Invoice getInvoice(@PathVariable String invoiceId) {
        return service.findInvoice(invoiceId)
                .map(mapper::toApi)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice with id" + invoiceId + " could not be found"));
    }

    @PostMapping
    Invoice addInvoice(@Validated @RequestBody Invoice invoice) {
        return mapper.toApi(service.addInvoice(mapper.toEntity(invoice)));
    }

    @DeleteMapping("{invoiceId}")
    Invoice deleteInvoice(@PathVariable String invoiceId) {
        return service.deleteInvoice(invoiceId)
                .map(mapper::toApi)
                .orElse(null);
    }
}
