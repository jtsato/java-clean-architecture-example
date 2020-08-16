package io.github.jtsato.bookstore.core.author.gateway;

import java.util.List;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.common.paging.Page;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface FindAuthorsByIdsGateway {

    Page<Author> execute(final List<Long> ids);
}
