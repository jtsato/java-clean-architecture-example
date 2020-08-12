package io.github.jtsato.bookstore.entrypoint.rest.author.mapper;

import java.util.ArrayList;
import java.util.List;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.SearchAuthorsInnerResponse;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.SearchAuthorsResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchAuthorsPresenter {

    public static SearchAuthorsResponse of(final Page<Author> page) {
        final List<Author> authors = page.getContent();
        final List<SearchAuthorsInnerResponse> content = new ArrayList<>(authors.size());
        authors.forEach(author -> content.add(of(author)));
        return new SearchAuthorsResponse(content, page.getPageable());
    }

    private static SearchAuthorsInnerResponse of(final Author author) {
        return new SearchAuthorsInnerResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
