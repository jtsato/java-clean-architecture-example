package io.github.jtsato.bookstore.dataprovider.rabbitmq.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message.RegisterBookMessage;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.mapper.RegisterBookMessageConverter;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class RegisterBookDataProvider implements RegisterBookGateway {

    @Value("${bookstore.rabbitmq.exchange.register-book}")
    private String exchange;
    
    @Value("${bookstore.rabbitmq.routingkey.register-book}")
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
