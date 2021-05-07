package io.github.jtsato.bookstore.infra.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RemoveBookByIdGateway;
import io.github.jtsato.bookstore.infra.service.DispatcherAmqpProducer;

/**
 * @book Jorge Takeshi Sato 
 */

@Service
public class RemoveBookByIdProvider implements RemoveBookByIdGateway {

    @Value("${bookstore.amqp.exchange.remove-book-by-id}")
    private String exchange;

    @Value("${bookstore.amqp.routingkey.remove-book-by-id}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Optional<Book> execute(final Long id) {
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, id);
        return Optional.empty();
    }
}
