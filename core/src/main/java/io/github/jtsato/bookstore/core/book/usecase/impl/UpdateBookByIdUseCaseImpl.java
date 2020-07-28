package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.Optional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByTitleGateway;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.core.book.usecase.UpdateBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;
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
public class UpdateBookByIdUseCaseImpl implements UpdateBookByIdUseCase {

    private final UpdateBookByIdGateway updateBookGateway;

    private final GetAuthorByIdGateway getAuthorByIdGateway;

    private final GetBookByTitleGateway getBookByTitleGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public Book updateBookById(final UpdateBookByIdParameters parameters) {

        final Author author = getAuthorAndValidate(parameters.getAuthorId());

        checkDuplicatedTitleViolation(parameters.getId(), parameters.getTitle());

        final Book book = new Book(parameters.getId(), author, parameters.getTitle(), parameters.getPrice(), parameters.getAvailable(), null, getLocalDateTime.now());

        final Optional<Book> optional = updateBookGateway.updateBookById(book);

        return optional.orElseThrow(() -> new NotFoundException("validation.book.id.notfound", parameters.getId()));
    }

    private Author getAuthorAndValidate(final Long authorId) {

        final Optional<Author> optional = getAuthorByIdGateway.getAuthorById(authorId);

        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", authorId));
    }

    private void checkDuplicatedTitleViolation(final Long bookId, final String bookTitle) {

        final Optional<Book> optional = getBookByTitleGateway.getBookByTitle(bookTitle);

        if (!optional.isPresent()) {
            return;
        }

        final Book book = optional.get();
        
        if (!book.getId().equals(bookId)) {
            throw new UniqueConstraintException("validation.book.title.already.exists", bookTitle);
        }
    }
}
