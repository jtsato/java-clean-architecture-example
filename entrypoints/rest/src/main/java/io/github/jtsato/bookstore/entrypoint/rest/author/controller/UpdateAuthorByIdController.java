package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.UpdateAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;
import io.github.jtsato.bookstore.entrypoint.rest.author.api.UpdateAuthorByIdApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.UpdateAuthorByIdRequest;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.UpdateAuthorByIdResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.mapper.UpdateAuthorByIdPresenter;
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
@RequestMapping("/authors/{id}")
public class UpdateAuthorByIdController implements UpdateAuthorByIdApiMethod {

    private static final Logger log = LoggerFactory.getLogger(UpdateAuthorByIdController.class);

    private final UpdateAuthorByIdUseCase updateAuthorByIdUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public UpdateAuthorByIdResponse updateAuthorById(@PathVariable final Long id, @RequestBody @DefaultValue final UpdateAuthorByIdRequest request) {

        final String jsonRequest = JsonConverter.of(request);
        log.info("Starting Controller -> UpdateAuthorByIdController with {}", jsonRequest);

        final UpdateAuthorByIdParameters parameters = new UpdateAuthorByIdParameters(id, request.getName(), request.getGender(), request.getBirthdate());

        final Author author = updateAuthorByIdUseCase.updateAuthorById(parameters);

        return UpdateAuthorByIdPresenter.of(author);
    }
}
