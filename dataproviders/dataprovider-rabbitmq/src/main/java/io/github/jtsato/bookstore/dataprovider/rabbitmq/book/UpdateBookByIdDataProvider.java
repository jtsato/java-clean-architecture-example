package io.github.jtsato.bookstore.dataprovider.rabbitmq.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message.UpdateBookByIdMessage;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.mapper.UpdateBookByIdMessageConverter;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class UpdateBookByIdDataProvider implements UpdateBookByIdGateway {

    @Value("${bookstore.rabbitmq.exchange.update-book-by-id}")
    private String exchange;
    
    @Value("${bookstore.rabbitmq.routingkey.update-book-by-id}")
    private String routingKey;  
    
    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public Optional<Book> updateBookById(final Book book) {
        final UpdateBookByIdMessage updateBookByIdMessage = UpdateBookByIdMessageConverter.of(book);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, updateBookByIdMessage);
        return Optional.of(book);    
    }
}
