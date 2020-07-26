package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.Book;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RemoveBookByIdUseCase {

	Book removeBookById(final Long id);
}
