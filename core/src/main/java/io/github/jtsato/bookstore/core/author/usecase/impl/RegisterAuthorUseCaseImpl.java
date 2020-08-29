package io.github.jtsato.bookstore.core.author.usecase.impl;

import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Named;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.core.author.usecase.RegisterAuthorUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;
import io.github.jtsato.bookstore.core.common.EnumeratorUtils;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Named
@RequiredArgsConstructor
public class RegisterAuthorUseCaseImpl implements RegisterAuthorUseCase {

    private final RegisterAuthorGateway registerAuthorGateway;

    private final GetAuthorByNameGateway getAuthorByNameGateway;

    @Override
    public Author registerAuthor(@Valid final RegisterAuthorParameters parameters) {

        checkDuplicatedNameViolation(parameters.getName());

        final LocalDate birthdate = LocalDate.parse(parameters.getBirthdate());
        final Gender gender = EnumeratorUtils.valueOf(parameters.getGender(), Gender.class);
        final Author author = new Author(null, StringUtils.stripToNull(parameters.getName()), gender, birthdate);

        return registerAuthorGateway.execute(author);
    }

    private void checkDuplicatedNameViolation(final String authorName) {
        final Optional<Author> optional = getAuthorByNameGateway.execute(authorName);
        optional.ifPresent(this::throwUniqueConstraintException);
    }

    private void throwUniqueConstraintException(final Author author) {
        throw new UniqueConstraintException("validation.author.name.already.exists", author.getName());
    }
}
