package events;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import dto.Invoice;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = InvoiceEvent.InvoiceEventBuilder.class)
public class InvoiceEvent {
    Invoice invoice;
    InvoiceEventType type;

    @JsonPOJOBuilder(withPrefix = "")
    public static class InvoiceEventBuilder {
    }
}
