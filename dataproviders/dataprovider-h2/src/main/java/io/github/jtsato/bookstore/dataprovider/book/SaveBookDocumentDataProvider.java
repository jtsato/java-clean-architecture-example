package io.github.jtsato.bookstore.dataprovider.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookDocumentGateway;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookDocumentEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookDocumentMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookDocumentRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class SaveBookDocumentDataProvider implements SaveBookDocumentGateway {

    @Autowired
    BookDocumentRepository bookDocumentRepository;

    @Override
    public BookDocument saveBookDocument(final BookDocument bookDocument) {
        final BookDocumentEntity bookDocumentEntity = BookDocumentMapper.of(bookDocument);
        return BookDocumentMapper.of(bookDocumentRepository.saveAndFlush(bookDocumentEntity));
    }
}
