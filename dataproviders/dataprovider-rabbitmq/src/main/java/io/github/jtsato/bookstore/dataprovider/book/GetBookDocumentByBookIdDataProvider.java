package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.GetBookDocumentByBookIdGateway;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class GetBookDocumentByBookIdDataProvider implements GetBookDocumentByBookIdGateway {

    @Override
    public Optional<BookDocument> execute(final Long bookId) {
        return Optional.empty();
    }
}
