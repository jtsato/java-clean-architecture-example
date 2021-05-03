package io.github.jtsato.bookstore.infra.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.infra.book.domain.message.RegisterBookMessage;
import io.github.jtsato.bookstore.infra.book.mapper.RegisterBookMessageConverter;
import io.github.jtsato.bookstore.infra.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class RegisterBookProvider implements RegisterBookGateway {

    @Value("${bookstore.rabbitmq.exchange.register-book}")
    private String exchange;

    @Value("${bookstore.rabbitmq.routingkey.register-book}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Book execute(final Book book) {
        final RegisterBookMessage registerBookMessage = RegisterBookMessageConverter.of(book);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, registerBookMessage);
        return book;

    }
}
