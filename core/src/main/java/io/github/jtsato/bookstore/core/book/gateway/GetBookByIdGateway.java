package io.github.jtsato.bookstore.core.book.gateway;

import java.util.Optional;

import io.github.jtsato.bookstore.core.book.domain.Book;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetBookByIdGateway {

	Optional<Book> getBookById(final Long id);
}
