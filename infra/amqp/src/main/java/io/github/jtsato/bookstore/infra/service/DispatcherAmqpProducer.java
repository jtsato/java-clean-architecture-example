package io.github.jtsato.bookstore.infra.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class DispatcherAmqpProducer {

    private static final Logger log = LoggerFactory.getLogger(DispatcherAmqpProducer.class);

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getRabbitTemplate(final String exchange, final String routingKey) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(new ObjectMapper().findAndRegisterModules()));
        return rabbitTemplate;
    }

    public void sendMessage(final String exchange, final String routingKey, final Object object) {
        try {
            final RabbitTemplate rabbitTemplate = getRabbitTemplate(exchange, routingKey);
            final String message = new ObjectMapper().writeValueAsString(object);
            log.info("Starting AMQP: exchange: {}, routingKey: {}, message: {}", exchange, routingKey, message);
            rabbitTemplate.convertAndSend(object);
        } catch (final JsonProcessingException exception) {
            log.error("sendMessage: Exception: {}", exception.getMessage());
        }
    }
}
