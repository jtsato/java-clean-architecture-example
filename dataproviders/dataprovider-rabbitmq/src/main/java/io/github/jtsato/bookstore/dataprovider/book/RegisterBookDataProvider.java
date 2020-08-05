package io.github.jtsato.bookstore.dataprovider.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.dataprovider.book.domain.message.RegisterBookMessage;
import io.github.jtsato.bookstore.dataprovider.book.mapper.RegisterBookMessageConverter;
import io.github.jtsato.bookstore.dataprovider.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class RegisterBookDataProvider implements RegisterBookGateway {

    @Value("${bookstore.exchange.register-book}")
    private String exchange;

    @Value("${bookstore.routingkey.register-book}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Book registerBook(final Book book) {
        final RegisterBookMessage registerBookMessage = RegisterBookMessageConverter.of(book);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, registerBookMessage);
        return book;

    }
}
