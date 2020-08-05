package io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl;

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
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.RegisterBookController;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.RegisterBookRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.RegisterBookResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.RegisterBookPresenter;
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
@RequestMapping("/books")
public class RegisterBookControllerImpl implements RegisterBookController {

    private final RegisterBookUseCase registerBookUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RegisterBookResponse registerBook(@RequestBody @DefaultValue final RegisterBookRequest request) {

        log.debug("Starting Controller -> RegisterBookController with {}", JsonConverter.convert(request));

        final RegisterBookParameters parameters = new RegisterBookParameters(request.getAuthorId(),
                                                                             request.getTitle(),
                                                                             request.getPrice(),
                                                                             request.getAvailable());

        final Book book = registerBookUseCase.registerBook(parameters);

        return RegisterBookPresenter.of(book);
    }
}
