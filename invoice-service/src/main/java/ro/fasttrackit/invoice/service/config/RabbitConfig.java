package ro.fasttrackit.invoice.service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    FanoutExchange invoiceExchange() {
        return new FanoutExchange("invoice.fanout");
    }

    @Bean
    Queue invoiceQue() {
        return new AnonymousQueue();
    }

    @Bean
    Binding invoiceBinding(Queue invoiceQue, FanoutExchange invoiceExchange) {
        return BindingBuilder.bind(invoiceQue).to(invoiceExchange);
    }
}
