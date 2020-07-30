package io.github.jtsato.bookstore.core.book.usecase;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetBookDocumentByBookIdUseCase {

	BookDocument getBookDocumentByBookId(final Long id);
}
