package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.UpdateBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.UpdateBookByIdApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.UpdateBookByIdRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.UpdateBookByIdResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.UpdateBookByIdPresenter;
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
@RequestMapping("/books/{id}")
public class UpdateBookByIdController implements UpdateBookByIdApiMethod {

    private static final Logger log = LoggerFactory.getLogger(UpdateBookByIdController.class);

    private final UpdateBookByIdUseCase updateBookByIdUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UpdateBookByIdResponse updateBookById(@PathVariable final Long id, @RequestBody @DefaultValue final UpdateBookByIdRequest request) {

        log.debug("Starting Controller -> UpdateBookByIdController with {}", JsonConverter.convert(request));

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(id,
                                                                                 request.getAuthorId(),
                                                                                 request.getTitle(),
                                                                                 request.getPrice(),
                                                                                 request.getAvailable());

        final Book book = updateBookByIdUseCase.execute(parameters);

        return UpdateBookByIdPresenter.of(book);
    }
}
