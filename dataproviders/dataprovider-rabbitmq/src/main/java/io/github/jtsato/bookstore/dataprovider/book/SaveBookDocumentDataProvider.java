package io.github.jtsato.bookstore.dataprovider.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookDocumentGateway;
import io.github.jtsato.bookstore.dataprovider.book.domain.message.SaveBookDocumentMessage;
import io.github.jtsato.bookstore.dataprovider.book.mapper.SaveBookDocumentMessageConverter;
import io.github.jtsato.bookstore.dataprovider.service.DispatcherAmqpProducer;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class SaveBookDocumentDataProvider implements SaveBookDocumentGateway {

    @Value("${bookstore.exchange.save-book-document}")
    private String exchange;

    @Value("${bookstore.routingkey.save-book-document}")
    private String routingKey;

    @Autowired
    DispatcherAmqpProducer dispatcherAmqpProducer;

    @Override
    public BookDocument saveBookDocument(final BookDocument bookDocument) {
        final SaveBookDocumentMessage saveBookDocumentMessage = SaveBookDocumentMessageConverter.of(bookDocument);
        dispatcherAmqpProducer.sendMessage(exchange, routingKey, saveBookDocumentMessage);
        return bookDocument;
    }
}