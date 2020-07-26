package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RegisterBookUseCase {

    Book registerBook(final RegisterBookParameters parameters);
}
