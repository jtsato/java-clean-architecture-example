package io.github.jtsato.bookstore.infra.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookDocumentGateway;
import io.github.jtsato.bookstore.infra.book.domain.message.SaveBookDocumentMessage;
import io.github.jtsato.bookstore.infra.book.mapper.SaveBookDocumentMessageConverter;
import io.github.jtsato.bookstore.infra.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class SaveBookDocumentProvider implements SaveBookDocumentGateway {

    @Value("${bookstore.amqp.exchange.save-book-document}")
    private String exchange;

    @Value("${bookstore.amqp.routingkey.save-book-document}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public BookDocument execute(final BookDocument bookDocument) {
        final SaveBookDocumentMessage saveBookDocumentMessage = SaveBookDocumentMessageConverter.of(bookDocument);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, saveBookDocumentMessage);
        return bookDocument;
    }
}
