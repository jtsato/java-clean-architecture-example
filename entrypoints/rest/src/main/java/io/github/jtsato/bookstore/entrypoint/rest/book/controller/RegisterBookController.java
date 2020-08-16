package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.RegisterBookUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.RegisterBookApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.RegisterBookRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.RegisterBookResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.RegisterBookPresenter;
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
public class RegisterBookController implements RegisterBookApiMethod {

    private static final Logger log = LoggerFactory.getLogger(RegisterBookController.class);

    private final RegisterBookUseCase registerBookUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RegisterBookResponse registerBook(@RequestBody @DefaultValue final RegisterBookRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> RegisterBookController with {}", jsonRequest);

        final RegisterBookParameters parameters = new RegisterBookParameters(request.getAuthorId(),
                                                                             request.getTitle(),
                                                                             request.getPrice(),
                                                                             request.getAvailable());

        final Book book = registerBookUseCase.execute(parameters);

        return RegisterBookPresenter.of(book);
    }
}
