package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.SearchBooksUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.SearchBooksApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.SearchBooksAuthorRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.SearchBooksRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SearchBooksResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.SearchBooksPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.RequiredArgsConstructor;

/*
 * A EntryPoint follows these steps:
 *
 * - Maps HTTP requests to Java objects
 * - Performs authorization checks
 * - Maps input to the input model of the use case
 * - Calls the use case
 * - Maps the output of the use case back to HTTP Returns an HTTP response
 */

/**
 * @author Jorge Takeshi Sato
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class SearchBooksController implements SearchBooksApiMethod {

    private static final Logger log = LoggerFactory.getLogger(SearchBooksController.class);

    private final SearchBooksUseCase searchBooksUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SearchBooksResponse execute(final Pageable pageable, @DefaultValue final SearchBooksRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> SearchBooksController with {}", jsonRequest);

        final SearchAuthorsParameters authorParameters = buildAuthorParameters(request);
        final ImmutablePair<BigDecimal, BigDecimal> priceRange = new ImmutablePair<>(request.getStartPrice(), request.getEndPrice());
        final ImmutablePair<String, String> creationDateRange = new ImmutablePair<>(request.getStartCreationDate(), request.getEndCreationDate());
        final ImmutablePair<String, String> updateDateRange = new ImmutablePair<>(request.getStartUpdateDate(), request.getEndUpdateDate());

        final SearchBooksParameters parameters = new SearchBooksParameters(authorParameters,
                                                                           request.getTitle(),
                                                                           priceRange,
                                                                           request.getAvailable(),
                                                                           creationDateRange,
                                                                           updateDateRange);

        final Page<Book> books = searchBooksUseCase.execute(parameters, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());

        return SearchBooksPresenter.of(books);
    }

    private SearchAuthorsParameters buildAuthorParameters(final SearchBooksRequest searchBooksRequest) {
        final SearchBooksAuthorRequest author = searchBooksRequest.getAuthor();
        return new SearchAuthorsParameters(author.getId(), author.getName(), author.getGender(), author.getStartBirthdate(), author.getEndBirthdate());
    }
}
