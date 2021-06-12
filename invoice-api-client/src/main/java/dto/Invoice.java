package dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = Invoice.InvoiceBuilder.class)
public class Invoice {
    String id;
    String description;
    Double amount;
    String sender;
    String receiver;
    Boolean payed;

    @JsonPOJOBuilder(withPrefix = "")
    public static class InvoiceBuilder {
    }
}
