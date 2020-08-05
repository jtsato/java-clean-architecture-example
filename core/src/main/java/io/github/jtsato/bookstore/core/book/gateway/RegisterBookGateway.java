package io.github.jtsato.bookstore.core.book.gateway;

import io.github.jtsato.bookstore.core.book.domain.Book;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RegisterBookGateway {

    Book registerBook(final Book book);

}
