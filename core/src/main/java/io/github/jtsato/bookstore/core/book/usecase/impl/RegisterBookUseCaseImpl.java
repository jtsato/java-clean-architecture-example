package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.Optional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByTitleGateway;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.core.book.usecase.RegisterBookUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;
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

@RequiredArgsConstructor
public class RegisterBookUseCaseImpl implements RegisterBookUseCase {

    private final RegisterBookGateway registerBookGateway;

    private final GetAuthorByIdGateway getAuthorByIdGateway;

    private final GetBookByTitleGateway getBookByTitleGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public Book execute(final RegisterBookParameters parameters) {

        final Author author = getAuthorAndValidate(parameters.getAuthorId());

        checkDuplicatedTitleViolation(parameters.getTitle());

        final Book book = new Book(null,
                                   author,
                                   parameters.getTitle(),
                                   parameters.getPrice(),
                                   parameters.getAvailable(),
                                   getLocalDateTime.now(),
                                   getLocalDateTime.now());

        return registerBookGateway.registerBook(book);
    }

    private Author getAuthorAndValidate(final Long authorId) {
        final Optional<Author> optional = getAuthorByIdGateway.getAuthorById(authorId);
        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", authorId));
    }

    private void checkDuplicatedTitleViolation(final String title) {
        final Optional<Book> optional = getBookByTitleGateway.getBookByTitle(title);
        optional.ifPresent(this::throwUniqueConstraintException);        
    }

    private void throwUniqueConstraintException(final Book book) {
        throw new UniqueConstraintException("validation.book.title.already.exists", book.getTitle());
    }
}
