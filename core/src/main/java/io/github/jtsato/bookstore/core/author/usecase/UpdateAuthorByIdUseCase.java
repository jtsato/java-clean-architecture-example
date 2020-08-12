package io.github.jtsato.bookstore.core.author.usecase;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface UpdateAuthorByIdUseCase {

    Author updateAuthorById(final UpdateAuthorByIdParameters parameters);
}
