package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class RemoveAuthorByIdDataProvider implements RemoveAuthorByIdGateway {

    @Value("${bookstore.exchange.remove-author-by-id}")
    private String exchange;

    @Value("${bookstore.routingkey.remove-author-by-id}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Optional<Author> execute(final Long id) {
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, id);
        return Optional.empty();
    }
}
