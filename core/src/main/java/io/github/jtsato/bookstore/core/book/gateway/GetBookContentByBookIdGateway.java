package io.github.jtsato.bookstore.core.book.gateway;

import java.util.Optional;

import io.github.jtsato.bookstore.core.book.domain.BookContent;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetBookContentByBookIdGateway {

	Optional<BookContent> getBookContentByBookId(final Long bookId);
}
