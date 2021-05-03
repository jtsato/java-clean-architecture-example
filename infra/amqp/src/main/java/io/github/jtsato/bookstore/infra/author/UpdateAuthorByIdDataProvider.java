package io.github.jtsato.bookstore.infra.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.infra.author.domain.message.UpdateAuthorByIdMessage;
import io.github.jtsato.bookstore.infra.author.mapper.UpdateAuthorByIdMessageConverter;
import io.github.jtsato.bookstore.infra.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class UpdateAuthorByIdDataProvider implements UpdateAuthorByIdGateway {

    @Value("${bookstore.rabbitmq.exchange.update-author-by-id}")
    private String exchange;

    @Value("${bookstore.rabbitmq.routingkey.update-author-by-id}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Optional<Author> execute(final Author author) {
        final UpdateAuthorByIdMessage updateAuthorByIdMessage = UpdateAuthorByIdMessageConverter.of(author);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, updateAuthorByIdMessage);
        return Optional.of(author);
    }
}
