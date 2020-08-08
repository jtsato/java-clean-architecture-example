package io.github.jtsato.bookstore.core.author.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface UpdateAuthorByIdUseCase {

    Author updateAuthorById(final UpdateAuthorByIdParameters parameters);
}
