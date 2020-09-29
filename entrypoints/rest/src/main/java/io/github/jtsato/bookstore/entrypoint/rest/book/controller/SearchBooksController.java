package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SearchBooksWrapperResponse;
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

@PreAuthorize("hasAuthority('bookstore-user')")
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
    public SearchBooksWrapperResponse execute(final Pageable pageable, @DefaultValue final SearchBooksRequest searchBooksRequest) {

        final String jsonRequest = JsonConverter.of(searchBooksRequest);
        log.info("Starting Controller -> SearchBooksController with {}", jsonRequest);

        final SearchBooksParameters parameters = buildSearchBooksParameters(searchBooksRequest);
        final Page<Book> books = searchBooksUseCase.execute(parameters, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());

        return SearchBooksPresenter.of(books);
    }

    private SearchBooksParameters buildSearchBooksParameters(final SearchBooksRequest searchBooksRequest) {
        final SearchAuthorsParameters searchAuthorsParameters = buildAuthorParameters(searchBooksRequest.getAuthor());
        final String title = searchBooksRequest.getTitle();
        final Boolean available = searchBooksRequest.getAvailable();
        final ImmutablePair<BigDecimal, BigDecimal> priceRange = new ImmutablePair<>(searchBooksRequest.getStartPrice(), searchBooksRequest.getEndPrice());
        final ImmutablePair<String, String> creationDateRange = new ImmutablePair<>(searchBooksRequest.getStartCreationDate(), searchBooksRequest.getEndCreationDate());
        final ImmutablePair<String, String> updateDateRange = new ImmutablePair<>(searchBooksRequest.getStartUpdateDate(), searchBooksRequest.getEndUpdateDate());
        return new SearchBooksParameters(searchAuthorsParameters, title, priceRange, available, creationDateRange, updateDateRange);
    }

    private SearchAuthorsParameters buildAuthorParameters(final SearchBooksAuthorRequest searchBooksAuthorRequest) {
        final Long id = searchBooksAuthorRequest.getId();
        final String name = searchBooksAuthorRequest.getName();
        final String gender = searchBooksAuthorRequest.getGender();
        final String startBirthdate = searchBooksAuthorRequest.getStartBirthdate();
        final String endBirthDate = searchBooksAuthorRequest.getEndBirthdate();
        return new SearchAuthorsParameters(id, name, gender, startBirthdate, endBirthDate);
    }
}
