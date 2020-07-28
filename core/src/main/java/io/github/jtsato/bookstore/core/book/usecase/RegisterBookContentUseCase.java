package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookContentParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RegisterBookContentUseCase {

    BookContent registerBookContent(final RegisterBookContentParameters parameters);
}
