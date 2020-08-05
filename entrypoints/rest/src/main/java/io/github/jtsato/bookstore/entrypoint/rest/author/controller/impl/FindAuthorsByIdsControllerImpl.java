package io.github.jtsato.bookstore.entrypoint.rest.author.controller.impl;

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
import io.github.jtsato.bookstore.entrypoint.rest.author.controller.FindAuthorsByIdsController;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.FindAuthorsByIdsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.FindAuthorsByIdsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.FindAuthorsByIdsPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class FindAuthorsByIdsControllerImpl implements FindAuthorsByIdsController {

    private final FindAuthorsByIdsUseCase findAuthorsByIdsUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/findByIds")
    public FindAuthorsByIdsResponse findAuthorsByIds(@RequestBody @DefaultValue final FindAuthorsByIdsRequest findAuthorsByIdsRequest) {

        log.debug("Starting Controller -> FindAuthorsByIdsController with {}", JsonConverter.convert(findAuthorsByIdsRequest));

        final Page<Author> authors = findAuthorsByIdsUseCase.findAuthorsByIds(findAuthorsByIdsRequest.getIds());

        return FindAuthorsByIdsPresenter.of(authors);
    }
}
