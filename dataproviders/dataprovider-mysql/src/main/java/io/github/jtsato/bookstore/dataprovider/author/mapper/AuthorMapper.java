package io.github.jtsato.bookstore.dataprovider.author.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.common.EnumeratorUtils;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorMapper {

    public static Author of(final AuthorEntity authorEntity) {
        final Gender gender = EnumeratorUtils.valueOf(authorEntity.getGender(), Gender.class);
        return new Author(authorEntity.getId(), authorEntity.getName(), gender, authorEntity.getBirthdate());
    }

    public static AuthorEntity of(final Author author) {
        return new AuthorEntity(author.getId(), author.getName(), author.getGender().name(), author.getBirthdate());
    }
}