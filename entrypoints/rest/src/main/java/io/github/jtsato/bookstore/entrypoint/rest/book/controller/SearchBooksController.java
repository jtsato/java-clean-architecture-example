package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.SearchBooksUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.book.api.SearchBooksApiMethod;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.SearchBooksAuthorRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.SearchBooksRequest;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SearchBooksResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.mapper.SearchBooksPresenter;
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
public class SearchBooksController implements SearchBooksApiMethod {

    private final SearchBooksUseCase searchBooksUseCase;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public SearchBooksResponse searchBooks(@PageableDefault(page = 0, size = 20) 
                                           @SortDefault.SortDefaults({@SortDefault(sort = "title", direction = Sort.Direction.ASC)}) final Pageable pageable,
                                           @DefaultValue final SearchBooksRequest request) {

        log.debug("Starting Controller -> SearchBooksController with {}", JsonConverter.convert(request));

        final SearchBooksParameters parameters = new SearchBooksParameters(request.getTitle(),
                                                                           buildParameters(request),
                                                                           request.getStartCreationDate(),
                                                                           request.getEndCreationDate());

        final Page<Book> books = searchBooksUseCase.execute(parameters, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort().toString());

        return SearchBooksPresenter.of(books);
    }

    private SearchAuthorsParameters buildParameters(final SearchBooksRequest searchBooksRequest) {
        final SearchBooksAuthorRequest author = searchBooksRequest.getAuthor();
        return new SearchAuthorsParameters(author.getId(), author.getName(), author.getGender(), author.getStartBirthday(), author.getEndBirthday());
    }
}
