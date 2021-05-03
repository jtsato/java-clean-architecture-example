package io.github.jtsato.bookstore.core.author.action;

import java.time.LocalDate;
import java.util.Optional;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.UpdateAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;
import io.github.jtsato.bookstore.core.common.EnumeratorUtils;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Named
@RequiredArgsConstructor
public class UpdateAuthorByIdAction implements UpdateAuthorByIdUseCase {

    private final UpdateAuthorByIdGateway updateAuthorGateway;

    private final GetAuthorByNameGateway getAuthorByNameGateway;

    @Override
    public Author updateAuthorById(final UpdateAuthorByIdParameters parameters) {

        checkDuplicatedNameViolation(parameters.getId(), parameters.getName());

        final LocalDate birthdate = LocalDate.parse(parameters.getBirthdate());
        final Gender gender = EnumeratorUtils.valueOf(parameters.getGender(), Gender.class);
        final Author author = new Author(parameters.getId(), StringUtils.stripToEmpty(parameters.getName()), gender, birthdate);

        final Optional<Author> optional = updateAuthorGateway.execute(author);

        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", String.valueOf(author.getId())));
    }

    private void checkDuplicatedNameViolation(final Long authorId, final String authorName) {

        final Optional<Author> optional = getAuthorByNameGateway.execute(authorName);

        if (optional.isEmpty()) {
            return;
        }

        final Author author = optional.get();

        if (!author.getId().equals(authorId)) {
            throw new UniqueConstraintException("validation.author.name.already.exists", authorName);
        }
    }
}
