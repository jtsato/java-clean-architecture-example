package io.github.jtsato.bookstore.dataprovider.rabbitmq.service;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jorge Takeshi Sato  
 */

@Slf4j
@Getter
@Setter
@Component
public class DispatcherAmqpProducer {

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
        } catch (JsonProcessingException exception) {
            log.error("RabbitMQService: Exception: {}", exception);
        }
    }
}
