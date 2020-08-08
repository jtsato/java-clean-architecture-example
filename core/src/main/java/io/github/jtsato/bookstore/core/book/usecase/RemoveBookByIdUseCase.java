package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface RemoveBookByIdUseCase {

    Book execute(final Long id);
}
