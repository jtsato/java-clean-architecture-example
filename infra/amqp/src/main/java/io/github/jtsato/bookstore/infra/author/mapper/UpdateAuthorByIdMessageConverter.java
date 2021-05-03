package io.github.jtsato.bookstore.infra.author.mapper;

import java.time.format.DateTimeFormatter;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.infra.author.domain.message.UpdateAuthorByIdMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAuthorByIdMessageConverter {

    public static UpdateAuthorByIdMessage of(final Author author) {
        return new UpdateAuthorByIdMessage(author.getId(),
                                           author.getName(),
                                           author.getGender().toString(),
                                           author.getBirthdate().format(DateTimeFormatter.ISO_DATE));
    }
}
