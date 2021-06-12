package ro.fasttrackit.invoice.service.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "invoices")
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceEntity {
    @Id
    private String id;

    private String description;
    private Double amount;
    private String sender;
    private String receiver;
    private Boolean payed;
}
