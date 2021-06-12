package ro.fasttrackit.invoice.service.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import ro.fasttrackit.curs13homework.filters.InvoiceFilters;
import ro.fasttrackit.invoice.service.model.entity.InvoiceEntity;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

@Repository
@RequiredArgsConstructor
public class InvoiceDao {
    private final MongoTemplate mongo;

    public List<InvoiceEntity> findAllInvoices(InvoiceFilters filters) {
        Query query = new Query();
        List<Criteria> criteria = buildCriteria(filters);

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
        }

        return mongo.find(query, InvoiceEntity.class);
    }

    private List<Criteria> buildCriteria(InvoiceFilters filters) {
        List<Criteria> criteria = new ArrayList<>();

        ofNullable(filters.getId())
                .ifPresent(id -> criteria.add(Criteria.where("id").is(id)));
        ofNullable(filters.getAmount())
                .ifPresent(amount -> criteria.add(Criteria.where("amount").is(amount)));
        ofNullable(filters.getSender())
                .ifPresent(sender -> criteria.add(Criteria.where("sender").regex(sender, "i")));
        ofNullable(filters.getReceiver())
                .ifPresent(receiver -> criteria.add(Criteria.where("receiver").regex(receiver, "i")));
        ofNullable(filters.getPayed())
                .ifPresent(payed -> criteria.add(Criteria.where("payed").is(payed)));
        return criteria;
    }
}
