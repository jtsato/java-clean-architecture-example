package io.github.jtsato.bookstore.entrypoint.rest.enumerator.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.usecase.SearchEnumeratorsUseCase;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;
import io.github.jtsato.bookstore.entrypoint.rest.common.metric.LogExecutionTime;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.domain.response.EnumeratorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.mapper.SearchEnumeratorsPresenter;
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
@RequestMapping("/enumerators")
public class SearchEnumeratorsController implements SearchEnumeratorsApiMethod {

    private final SearchEnumeratorsUseCase searchEnumeratorsUseCase;

    private final SearchEnumeratorsPresenter searchEnumeratorsPresenter;

    @Override
    @LogExecutionTime
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EnumeratorResponse> execute(@RequestParam(required = false) final String domain, @RequestParam(required = false) final String key) {

        log.debug("Starting Controller -> SearchEnumeratorsController with domain {}, and key {}", domain, key);

        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(Optional.ofNullable(StringUtils.stripToNull(domain)),
                                                                                       Optional.ofNullable(StringUtils.stripToNull(key)));

        final List<Enumerator> enumerators = searchEnumeratorsUseCase.execute(parameters);

        return searchEnumeratorsPresenter.of(enumerators);
    }
}