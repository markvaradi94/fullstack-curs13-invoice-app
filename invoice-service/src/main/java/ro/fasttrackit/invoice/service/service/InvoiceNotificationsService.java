package ro.fasttrackit.invoice.service.service;

import events.InvoiceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;
import ro.fasttrackit.invoice.service.model.mappers.InvoiceMapper;

import static events.InvoiceEventType.CREATED;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceNotificationsService {
    private final InvoiceMapper mapper;
    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange invoiceExchange;

    public void notifyInvoiceCreated(InvoiceEntity invoice) {
        InvoiceEvent event = InvoiceEvent.builder()
                .invoice(mapper.toApi(invoice))
                .type(CREATED)
                .build();
        log.info("Sending event: " + event);
        rabbitTemplate.convertAndSend(invoiceExchange.getName(), "", event);
    }
}
