package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface UpdateBookByIdUseCase {

    Book execute(final UpdateBookByIdParameters parameters);
}
