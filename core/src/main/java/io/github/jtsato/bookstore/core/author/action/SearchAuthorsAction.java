package io.github.jtsato.bookstore.core.author.action;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.usecase.SearchAuthorsUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import lombok.RequiredArgsConstructor;

/*
 * A Use Case follows these steps:
 * - Takes input
 * - Validates business rules
 * - Manipulates the model state
 * - Returns output
 */

/**
 * @author Jorge Takeshi Sato
 */

@Named
@RequiredArgsConstructor
public class SearchAuthorsAction implements SearchAuthorsUseCase {

    private final SearchAuthorsGateway searchAuthorsGateway;

    @Override
    public Page<Author> searchAuthors(final SearchAuthorsParameters parameters, final Integer page, final Integer size, final String orderBy) {
        return searchAuthorsGateway.execute(parameters, page, size, orderBy);
    }
}
