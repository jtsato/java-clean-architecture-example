package io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.FindBooksByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.FindBooksByIdsController;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.FindBooksByIdsRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.FindBooksByIdsResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.FindBooksByIdsPresenter;
import io.github.jtsato.bookstore.entrypoint.rest.common.JsonConverter;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * A EntryPoint follows these steps:
 *
 * - Maps HTTP requests to Java objects
 * - Performs bookization checks
 * - Maps input to the input model of the use case
 * - Calls the use case
 * - Maps the output of the use case back to HTTP Returns an HTTP response
 */

/**
 * @book Jorge Takeshi Sato  
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class FindBooksByIdsControllerImpl implements FindBooksByIdsController {

    private final FindBooksByIdsUseCase findBooksByIdsUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/findByIds")
    public FindBooksByIdsResponse findBooksByIds(@RequestBody @DefaultValue final FindBooksByIdsRequest findBooksByIdsRequest) {

        log.debug("Starting Controller -> FindBooksByIdsController with {}", JsonConverter.convert(findBooksByIdsRequest));

        final Page<Book> books = findBooksByIdsUseCase.execute(findBooksByIdsRequest.getIds());

        return FindBooksByIdsPresenter.of(books);
    }
}

