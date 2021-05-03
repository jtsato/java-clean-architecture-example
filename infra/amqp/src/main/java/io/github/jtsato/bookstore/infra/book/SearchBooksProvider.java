package io.github.jtsato.bookstore.infra.book;

import java.util.Collections;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class SearchBooksProvider implements SearchBooksGateway {

    @Override
    public Page<Book> execute(final SearchBooksParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {
        return new PageImpl<>(Collections.emptyList(), new Pageable(pageNumber, size, 0, 0L, 0));
    }
}
