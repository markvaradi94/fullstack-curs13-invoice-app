package ro.fasttrackit.invoice.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;

public interface InvoiceRepository extends MongoRepository<InvoiceEntity, String> {
}
