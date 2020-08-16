package io.github.jtsato.bookstore.dataprovider.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.message.RegisterAuthorMessage;
import io.github.jtsato.bookstore.dataprovider.author.mapper.RegisterAuthorMessageConverter;
import io.github.jtsato.bookstore.dataprovider.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class RegisterAuthorDataProvider implements RegisterAuthorGateway {

    @Value("${bookstore.rabbitmq.exchange.register-author}")
    private String exchange;

    @Value("${bookstore.rabbitmq.routingkey.register-author}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Author execute(final Author author) {
        final RegisterAuthorMessage registerAuthorMessage = RegisterAuthorMessageConverter.of(author);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, registerAuthorMessage);
        return author;
    }
}
