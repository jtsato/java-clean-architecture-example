package io.github.jtsato.bookstore.entrypoint.rest.author.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.response.UpdateAuthorByIdResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAuthorByIdPresenter {

    public static UpdateAuthorByIdResponse of(final Author author) {
        return new UpdateAuthorByIdResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
