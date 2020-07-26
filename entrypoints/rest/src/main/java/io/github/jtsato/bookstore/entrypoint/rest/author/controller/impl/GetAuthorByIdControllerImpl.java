package io.github.jtsato.bookstore.entrypoint.rest.author.controller.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.GetAuthorByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.author.controller.GetAuthorByIdController;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.GetAuthorByIdResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.GetAuthorByIdPresenter;
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
public class GetAuthorByIdControllerImpl implements GetAuthorByIdController {

    private final GetAuthorByIdUseCase getAuthorByIdUseCase;

    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetAuthorByIdResponse getAuthorById(@PathVariable final Long id) {

        log.debug("Starting Controller -> GetAuthorByIdController with {}", id);

        final Author author = getAuthorByIdUseCase.getAuthorById(id);

        return GetAuthorByIdPresenter.of(author);
    }
}