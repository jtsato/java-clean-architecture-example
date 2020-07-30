package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface SaveBookDocumentUseCase {

    BookDocument saveBookDocument(final SaveBookDocumentParameters parameters);
}
