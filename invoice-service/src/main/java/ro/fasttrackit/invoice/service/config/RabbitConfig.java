package ro.fasttrackit.invoice.service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitConfig {
    private final ConnectionFactory connectionFactory;

    @Bean
    FanoutExchange paymentExchange() {
        return new FanoutExchange("payment.exchange");
    }

    @Bean
    Queue paymentQue() {
        return new AnonymousQueue();
    }

    @Bean
    Binding paymentBinding(Queue paymentQue, FanoutExchange paymentExchange) {
        return BindingBuilder.bind(paymentQue).to(paymentExchange);
    }

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
