package io.github.jtsato.bookstore.core.author.usecase;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface SearchAuthorsUseCase {

    Page<Author> searchAuthors(final SearchAuthorsParameters parameters, final Integer page, final Integer size, final String orderBy);
}
