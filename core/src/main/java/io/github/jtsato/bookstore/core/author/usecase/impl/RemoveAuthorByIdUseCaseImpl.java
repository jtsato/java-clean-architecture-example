package io.github.jtsato.bookstore.core.author.usecase.impl;

import java.math.BigDecimal;
import java.util.Optional;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

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

@Named
@RequiredArgsConstructor
public class RemoveAuthorByIdUseCaseImpl implements RemoveAuthorByIdUseCase {

    private final RemoveAuthorByIdGateway removeAuthorByIdGateway;

    private final SearchBooksGateway searchBooksGateway;

    @Override
    public Author removeAuthorById(final Long id) {

        if (id == null) {
            throw new InvalidParameterException("validation.author.id.null");
        }

        final SearchAuthorsParameters searchAuthorsParameters = new SearchAuthorsParameters(id, null, null, null, null);
        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>(null, null);

        final SearchBooksParameters parameters = new SearchBooksParameters(searchAuthorsParameters, null, prices, null, creationDates, updateDates);

        final Page<Book> pageOfBook = searchBooksGateway.execute(parameters, 0, 1, null);

        if (CollectionUtils.isNotEmpty(pageOfBook.getContent())) {
            throw new ParentConstraintException("validation.author.with.books.removal");
        }

        final Optional<Author> optional = removeAuthorByIdGateway.execute(id);

        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", id));
    }
}
