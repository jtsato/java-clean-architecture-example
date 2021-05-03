package io.github.jtsato.bookstore.core.author.action;

import java.util.Optional;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.GetAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

/*
 * A Use Case follows these steps:
 * - Takes input
 * - Validates business rules
 * - Manipulates the model state
 * - Returns output
 */

/**
 * @author Jorge Takeshi Sato
 */

@Named
@RequiredArgsConstructor
public class GetAuthorByIdAction implements GetAuthorByIdUseCase {

    private final GetAuthorByIdGateway getAuthorByIdGateway;

    @Override
    public Author getAuthorById(final Long id) {

        if (id == null) {
            throw new InvalidParameterException("validation.author.id.null");
        }

        final Optional<Author> optional = getAuthorByIdGateway.execute(id);

        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", String.valueOf(id)));
    }
}
