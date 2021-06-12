package client;

import dto.Invoice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ro.fasttrackit.curs13homework.filters.InvoiceFilters;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpEntity.EMPTY;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Component
@RequiredArgsConstructor
public class InvoiceApiClient {
    private static final String HOSTNAME = "invoice-service";
    private final RestTemplate restTemplate;

    public List<Invoice> getAllInvoices(InvoiceFilters filters) {
        String url = buildQueriedUrl(filters);
        return restTemplate.exchange(
                url,
                GET,
                EMPTY,
                new ParameterizedTypeReference<List<Invoice>>() {
                }
        ).getBody();
    }

    public Optional<Invoice> getInvoice(String invoiceId) {
        String url = UriComponentsBuilder.fromHttpUrl(HOSTNAME)
                .path("/invoices/")
                .path(invoiceId)
                .toUriString();
        try {
            return ofNullable(restTemplate.getForObject(url, Invoice.class));
        } catch (HttpClientErrorException exception) {
            return empty();
        }
    }

    public Invoice addInvoice(Invoice invoice) {
        String url = UriComponentsBuilder.fromHttpUrl(HOSTNAME)
                .path("/invoices")
                .toUriString();

        return restTemplate.postForObject(url, invoice, Invoice.class);
    }

    public Invoice deleteInvoice(String invoiceId) {
        String url = UriComponentsBuilder.fromHttpUrl(HOSTNAME)
                .path("/invoices/")
                .path(invoiceId)
                .toUriString();
        return restTemplate.exchange(
                url,
                DELETE,
                EMPTY,
                Invoice.class
        ).getBody();
    }

    private String buildQueriedUrl(InvoiceFilters filters) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOSTNAME);
        ofNullable(filters.getId())
                .ifPresent(id -> builder.queryParam("id", id));
        ofNullable(filters.getAmount())
                .ifPresent(amount -> builder.queryParam("amount", amount));
        ofNullable(filters.getSender())
                .ifPresent(sender -> builder.queryParam("sender", sender));
        ofNullable(filters.getReceiver())
                .ifPresent(receiver -> builder.queryParam("receiver", receiver));
        ofNullable(filters.getPayed())
                .ifPresent(payed -> builder.queryParam("payed", payed));
        return builder.toUriString();
    }


}
