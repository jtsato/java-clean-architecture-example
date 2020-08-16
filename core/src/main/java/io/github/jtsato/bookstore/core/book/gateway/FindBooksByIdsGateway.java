package io.github.jtsato.bookstore.core.book.gateway;

import java.util.List;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface FindBooksByIdsGateway {

    Page<Book> execute(final List<Long> ids);
}
