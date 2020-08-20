package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.GetBookDocumentByBookIdGateway;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookDocumentEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookDocumentMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookDocumentRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
public class GetBookDocumentByBookIdDataProvider implements GetBookDocumentByBookIdGateway {

    @Autowired
    BookDocumentRepository bookDocumentRepository;

    @Override
    public Optional<BookDocument> execute(final Long bookId) {
        final Optional<BookDocumentEntity> optional = bookDocumentRepository.findByBookId(bookId);
        return optional.map(BookDocumentMapper::of);
    }
}
