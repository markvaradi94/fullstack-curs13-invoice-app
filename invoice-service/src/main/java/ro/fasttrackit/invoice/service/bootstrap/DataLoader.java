package ro.fasttrackit.invoice.service.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;
import ro.fasttrackit.invoice.service.service.InvoiceService;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final InvoiceService service;

    @Override
    public void run(String... args) throws Exception {
//        service.addInvoice(
//                InvoiceEntity.builder()
//                        .id("1")
//                        .amount(500.0)
//                        .description("rent")
//                        .payed(true)
//                        .receiver("Ioane")
//                        .sender("Gicu")
//                        .build()
//        );
//        service.addInvoice(
//                InvoiceEntity.builder()
//                        .id("2")
//                        .amount(120D)
//                        .description("food")
//                        .payed(false)
//                        .receiver("Ioana")
//                        .sender("Zoli")
//                        .build()
//        );
//        service.addInvoice(
//                InvoiceEntity.builder()
//                        .id("3")
//                        .amount(240.50)
//                        .description("bills")
//                        .payed(false)
//                        .receiver("Gyuszi")
//                        .sender("Stefan")
//                        .build()
//        );
//        service.addInvoice(
//                InvoiceEntity.builder()
//                        .id("4")
//                        .amount(990.0)
//                        .description("groceries")
//                        .payed(true)
//                        .receiver("Bela")
//                        .sender("Vasile")
//                        .build()
//        );
    }
}
