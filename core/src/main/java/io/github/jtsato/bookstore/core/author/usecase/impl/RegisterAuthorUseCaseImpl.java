package io.github.jtsato.bookstore.core.author.usecase.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.core.author.usecase.RegisterAuthorUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;
import io.github.jtsato.bookstore.core.common.EnumUtils;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@RequiredArgsConstructor
public class RegisterAuthorUseCaseImpl implements RegisterAuthorUseCase {

    private final RegisterAuthorGateway registerAuthorGateway;

    private final GetAuthorByNameGateway getAuthorByNameGateway;

    @Override
    public Author registerAuthor(@Valid final RegisterAuthorParameters parameters) {

        checkDuplicatedNameViolation(parameters.getName());

        final LocalDate birthday = LocalDate.parse(parameters.getBirthday());

        final Gender gender = EnumUtils.valueOf(parameters.getGender(), Gender.class);

        final Author author = new Author(null, parameters.getName(), gender, birthday);

        return registerAuthorGateway.registerAuthor(author);
    }

    private void checkDuplicatedNameViolation(final String authorName) {

        final Optional<Author> optional = getAuthorByNameGateway.getAuthorByName(authorName);

        if (optional.isPresent()) {
            throw new UniqueConstraintException("validation.author.name.already.exists", authorName);
        }
    }
}
