package io.github.jtsato.bookstore.core.author.usecase;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface RegisterAuthorUseCase {

    Author registerAuthor(final RegisterAuthorParameters parameters);
}
