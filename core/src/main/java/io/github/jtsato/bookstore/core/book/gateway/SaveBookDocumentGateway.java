package io.github.jtsato.bookstore.core.book.gateway;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface SaveBookDocumentGateway {

	BookDocument saveBookDocument(final BookDocument bookDocument);
}
