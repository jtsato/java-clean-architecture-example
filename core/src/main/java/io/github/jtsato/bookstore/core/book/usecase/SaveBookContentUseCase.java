package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookContentParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface SaveBookContentUseCase {

    BookContent saveBookContent(final SaveBookContentParameters parameters);
}
