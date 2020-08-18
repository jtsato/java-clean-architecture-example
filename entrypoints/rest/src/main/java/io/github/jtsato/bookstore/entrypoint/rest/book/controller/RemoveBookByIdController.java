package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.book.usecase.RemoveBookByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.RemoveBookByIdApiMethod;
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

@PreAuthorize("hasAuthority('bookstore-admin')")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class RemoveBookByIdController implements RemoveBookByIdApiMethod {

    private static final Logger log = LoggerFactory.getLogger(RemoveBookByIdController.class);

    private final RemoveBookByIdUseCase removeBookByIdUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void execute(@PathVariable final Long id) {

        log.info("Starting Controller -> RemoveBookByIdController with {}", id);

        removeBookByIdUseCase.execute(id);
    }
}
