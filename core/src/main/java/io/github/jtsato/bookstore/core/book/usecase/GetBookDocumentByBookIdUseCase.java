package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface GetBookDocumentByBookIdUseCase {

    BookDocument execute(final Long id);
}
