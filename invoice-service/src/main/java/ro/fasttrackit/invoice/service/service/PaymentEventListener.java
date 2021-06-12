package ro.fasttrackit.invoice.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ro.fasttrackit.curs13homework.payments.events.PaymentEvent;

import static ro.fasttrackit.curs13homework.payments.events.PaymentEventType.COMPLETED;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentEventListener {
    private final InvoiceService invoiceService;

    @RabbitListener(queues = "#{paymentQue.name}")
    void processPaymentEvent(PaymentEvent event) {
        if (event.getType() == COMPLETED) {
            invoiceService.setInvoiceToPaid(event.getPayment().getInvoiceId());
            log.info("Invoice with id " + event.getPayment().getInvoiceId() + " received payment: " + event.getPayment());
        }
    }

}
