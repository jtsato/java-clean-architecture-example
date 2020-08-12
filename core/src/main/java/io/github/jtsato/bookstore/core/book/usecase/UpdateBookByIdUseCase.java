package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface UpdateBookByIdUseCase {

    Book execute(final UpdateBookByIdParameters parameters);
}
