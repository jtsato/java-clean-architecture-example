package io.github.jtsato.bookstore.core.author.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Author;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface GetAuthorByIdUseCase {

    Author getAuthorById(final Long id);
}
