package io.github.jtsato.bookstore.core.book.gateway;

import java.util.Optional;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetBookDocumentByBookIdGateway {

    Optional<BookDocument> getBookDocumentByBookId(final Long bookId);
}
