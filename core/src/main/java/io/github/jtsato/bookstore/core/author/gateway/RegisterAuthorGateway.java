package io.github.jtsato.bookstore.core.author.gateway;

import io.github.jtsato.bookstore.core.author.domain.Author;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface RegisterAuthorGateway {

    Author execute(final Author author);
}
