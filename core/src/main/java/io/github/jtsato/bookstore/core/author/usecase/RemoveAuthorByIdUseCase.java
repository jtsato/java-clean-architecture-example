package io.github.jtsato.bookstore.core.author.usecase;

import io.github.jtsato.bookstore.core.author.domain.Author;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RemoveAuthorByIdUseCase {

    Author removeAuthorById(final Long id);
}
