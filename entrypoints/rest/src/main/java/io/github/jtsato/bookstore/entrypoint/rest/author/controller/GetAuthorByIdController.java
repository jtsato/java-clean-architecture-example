package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.GetAuthorByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.author.api.GetAuthorByIdApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.GetAuthorByIdResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.GetAuthorByIdPresenter;
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
public class GetAuthorByIdController implements GetAuthorByIdApiMethod {

    private static final Logger log = LoggerFactory.getLogger(GetAuthorByIdController.class);

    private final GetAuthorByIdUseCase getAuthorByIdUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetAuthorByIdResponse execute(@PathVariable final Long id) {

        log.info("Starting Controller -> GetAuthorByIdController with {}", id);

        final Author author = getAuthorByIdUseCase.getAuthorById(id);

        return GetAuthorByIdPresenter.of(author);
    }
}
