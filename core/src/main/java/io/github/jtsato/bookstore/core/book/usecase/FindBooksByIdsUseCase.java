package io.github.jtsato.bookstore.core.book.usecase;

import java.util.List;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface FindBooksByIdsUseCase {

    Page<Book> execute(final List<Long> id);
}
