package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.SearchAuthorsUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.author.api.SearchAuthorsApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.SearchAuthorsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.SearchAuthorsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.SearchAuthorsPresenter;
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
@RequestMapping("/authors")
public class SearchAuthorsController implements SearchAuthorsApiMethod {

    private static final Logger log = LoggerFactory.getLogger(SearchAuthorsController.class);

    private final SearchAuthorsUseCase searchAuthorsUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SearchAuthorsResponse execute(final Pageable pageable, @DefaultValue final SearchAuthorsRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> SearchAuthorsController with {}", jsonRequest);

        final SearchAuthorsParameters parameters = buildSearchAuthorsParameters(request);
        final Page<Author> authors = searchAuthorsUseCase.searchAuthors(parameters,
                                                                        pageable.getPageNumber(),
                                                                        pageable.getPageSize(),
                                                                        pageable.getSort().toString());

        return SearchAuthorsPresenter.of(authors);
    }

    private SearchAuthorsParameters buildSearchAuthorsParameters(final SearchAuthorsRequest request) {
        return new SearchAuthorsParameters(request.getId(), request.getName(), request.getGender(), request.getStartBirthdate(), request.getEndBirthdate());
    }
}
