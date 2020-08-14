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
import io.github.jtsato.bookstore.core.author.usecase.RegisterAuthorUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;
import io.github.jtsato.bookstore.entrypoint.rest.author.api.RegisterAuthorApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.RegisterAuthorRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.RegisterAuthorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.RegisterAuthorPresenter;
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
public class RegisterAuthorController implements RegisterAuthorApiMethod {

    private static final Logger log = LoggerFactory.getLogger(RegisterAuthorController.class);

    private final RegisterAuthorUseCase registerAuthorUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RegisterAuthorResponse registerAuthor(@RequestBody @DefaultValue final RegisterAuthorRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> RegisterAuthorController with {}", jsonRequest);

        final RegisterAuthorParameters parameters = new RegisterAuthorParameters(request.getName(), request.getGender(), request.getBirthdate());

        final Author author = registerAuthorUseCase.registerAuthor(parameters);

        return RegisterAuthorPresenter.of(author);
    }
}
