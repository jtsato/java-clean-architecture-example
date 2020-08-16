package io.github.jtsato.bookstore.core.book.usecase.impl;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.SearchBooksUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import lombok.RequiredArgsConstructor;

/*
 * A Use Case follows these steps:
 * - Takes input
 * - Validates business rules
 * - Manipulates the model state
 * - Returns output
 */

/**
 * @author Jorge Takeshi Sato
 */

@Named
@RequiredArgsConstructor
public class SearchBooksUseCaseImpl implements SearchBooksUseCase {

    private final SearchBooksGateway searchBooksGateway;

    @Override
    public Page<Book> execute(final SearchBooksParameters parameters, final Integer page, final Integer size, final String orderBy) {
        return searchBooksGateway.execute(parameters, page, size, orderBy);
    }
}
