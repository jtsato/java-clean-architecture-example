package io.github.jtsato.bookstore.core.author.usecase.impl;

import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.RemoveAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.ParentConstraintException;
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

@RequiredArgsConstructor
public class RemoveAuthorByIdUseCaseImpl implements RemoveAuthorByIdUseCase {

    private final RemoveAuthorByIdGateway removeAuthorByIdGateway;

    private final SearchBooksGateway searchBooksGateway;

    @Override
    public Author removeAuthorById(final Long id) {

        if (id == null) {
            throw new InvalidParameterException("validation.author.id.null");
        }

        final SearchAuthorsParameters authorsParameters = new SearchAuthorsParameters(id, null, null, null, null);
        final SearchBooksParameters parameters = new SearchBooksParameters(null, authorsParameters, null, null);

        final Page<Book> pageOfBook = searchBooksGateway.searchBooks(parameters, 0, 1, null);

        if (CollectionUtils.isNotEmpty(pageOfBook.getContent())) {
            throw new ParentConstraintException("validation.author.with.books.removal");
        }

        final Optional<Author> optional = removeAuthorByIdGateway.removeAuthorById(id);

        if (!optional.isPresent()) {
            throw new NotFoundException("validation.author.id.notfound", id);
        }

        return optional.get();
    }
}
