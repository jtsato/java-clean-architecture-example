package io.github.jtsato.bookstore.core.author.usecase;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@FunctionalInterface
public interface RegisterAuthorUseCase {

    Author registerAuthor(final RegisterAuthorParameters parameters);
}
