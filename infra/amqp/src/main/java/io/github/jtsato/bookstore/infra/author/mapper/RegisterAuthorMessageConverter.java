package io.github.jtsato.bookstore.infra.author.mapper;

import java.time.format.DateTimeFormatter;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.infra.author.domain.message.RegisterAuthorMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterAuthorMessageConverter {

    public static RegisterAuthorMessage of(final Author author) {
        return new RegisterAuthorMessage(author.getName(), author.getGender().toString(), author.getBirthdate().format(DateTimeFormatter.ISO_DATE));
    }
}
