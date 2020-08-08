package io.github.jtsato.bookstore.core.book.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface SearchBooksUseCase {

    Page<Book> execute(final SearchBooksParameters parameters, final Integer page, final Integer size, final String orderBy);
}
