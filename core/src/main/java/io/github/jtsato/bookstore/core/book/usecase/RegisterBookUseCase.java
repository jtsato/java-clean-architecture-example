package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface RegisterBookUseCase {

    Book execute(final RegisterBookParameters parameters);
}
