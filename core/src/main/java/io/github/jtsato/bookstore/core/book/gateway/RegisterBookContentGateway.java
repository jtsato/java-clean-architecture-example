package io.github.jtsato.bookstore.core.book.gateway;

import io.github.jtsato.bookstore.core.book.domain.BookContent;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RegisterBookContentGateway {

	BookContent registerBookContent(final BookContent bookContent);
}
