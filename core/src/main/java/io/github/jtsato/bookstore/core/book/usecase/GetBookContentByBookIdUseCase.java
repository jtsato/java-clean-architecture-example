package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.BookContent;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetBookContentByBookIdUseCase {

	BookContent getBookContentByBookId(final Long id);
}
