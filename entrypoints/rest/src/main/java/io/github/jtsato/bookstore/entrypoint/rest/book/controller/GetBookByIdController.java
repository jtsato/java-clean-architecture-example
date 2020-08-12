package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.GetBookByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.GetBookByIdApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.GetBookByIdResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.GetBookByIdPresenter;
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
public class GetBookByIdController implements GetBookByIdApiMethod {

    private static final Logger log = LoggerFactory.getLogger(GetBookByIdController.class);

    private final GetBookByIdUseCase getBookByIdUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GetBookByIdResponse getBookById(@PathVariable final Long id) {

        log.debug("Starting Controller -> GetBookByIdController with {}", id);

        final Book book = getBookByIdUseCase.execute(id);

        return GetBookByIdPresenter.of(book);
    }
}
