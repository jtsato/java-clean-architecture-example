package io.github.jtsato.bookstore.core.book.usecase;

import java.util.List;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface FindBooksByIdsUseCase {

    Page<Book> findBooksByIds(final List<Long> id);
}
