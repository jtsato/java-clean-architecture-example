package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.FindAuthorsByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.author.api.FindAuthorsByIdsApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.FindAuthorsByIdsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.FindAuthorsByIdsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.FindAuthorsByIdsPresenter;
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
@RequestMapping("/authors")
public class FindAuthorsByIdsController implements FindAuthorsByIdsApiMethod {

    private static final Logger log = LoggerFactory.getLogger(FindAuthorsByIdsController.class);

    private final FindAuthorsByIdsUseCase findAuthorsByIdsUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/findByIds")
    public FindAuthorsByIdsResponse execute(@RequestBody @DefaultValue final FindAuthorsByIdsRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> FindAuthorsByIdsController with {}", jsonRequest);

        final Page<Author> authors = findAuthorsByIdsUseCase.findAuthorsByIds(request.getIds());

        return FindAuthorsByIdsPresenter.of(authors);
    }
}
