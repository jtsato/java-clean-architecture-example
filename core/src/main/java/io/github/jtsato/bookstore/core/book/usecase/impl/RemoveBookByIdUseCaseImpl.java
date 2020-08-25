package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.Optional;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RemoveBookByIdGateway;
import io.github.jtsato.bookstore.core.book.usecase.RemoveBookByIdUseCase;
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
public class RemoveBookByIdUseCaseImpl implements RemoveBookByIdUseCase {

    private final RemoveBookByIdGateway removeBookByIdGateway;

    @Override
    public Book execute(final Long id) {

        if (id == null) {
            throw new InvalidParameterException("validation.book.id.null");
        }

        final Optional<Book> optional = removeBookByIdGateway.execute(id);

        return optional.orElseThrow(() -> new NotFoundException("validation.book.id.notfound", String.valueOf(id)));
    }
}
