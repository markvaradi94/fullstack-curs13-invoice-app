package ro.fasttrackit.invoice.service.model.mappers;

import dto.Invoice;
import org.springframework.stereotype.Component;
import ro.fasttrackit.curs13homework.utils.ModelMapper;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;

@Component
public class InvoiceMapper implements ModelMapper<Invoice, InvoiceEntity> {
    @Override
    public Invoice toApi(InvoiceEntity source) {
        return Invoice.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .sender(source.getSender())
                .receiver(source.getReceiver())
                .payed(source.getPayed())
                .build();
    }

    @Override
    public InvoiceEntity toEntity(Invoice source) {
        return InvoiceEntity.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .sender(source.getSender())
                .receiver(source.getReceiver())
                .payed(source.getPayed())
                .build();
    }
}
