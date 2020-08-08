package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface SaveBookDocumentUseCase {

    BookDocument execute(final SaveBookDocumentParameters parameters);
}
