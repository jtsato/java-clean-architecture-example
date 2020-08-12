package io.github.jtsato.bookstore.entrypoint.rest.author.mapper;

import java.util.ArrayList;
import java.util.List;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.FindAuthorsByIdInnerResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.FindAuthorsByIdsResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindAuthorsByIdsPresenter {

    public static FindAuthorsByIdsResponse of(final Page<Author> page) {
        final List<Author> authors = page.getContent();
        final List<FindAuthorsByIdInnerResponse> content = new ArrayList<>(authors.size());
        authors.forEach(author -> content.add(of(author)));
        return new FindAuthorsByIdsResponse(content, page.getPageable());
    }

    private static FindAuthorsByIdInnerResponse of(final Author author) {
        return new FindAuthorsByIdInnerResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
