package io.github.jtsato.bookstore.entrypoint.rest.author.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.GetAuthorByIdResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAuthorByIdPresenter {

    public static GetAuthorByIdResponse of(final Author author) {
        return new GetAuthorByIdResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
