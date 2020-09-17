package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

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

@Named
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

        final String title = StringUtils.stripToEmpty(parameters.getTitle());
        final BigDecimal price = parameters.getPrice();
        final Boolean available = parameters.getAvailable();
        final LocalDateTime creationDate = getLocalDateTime.now();
        final LocalDateTime updateDate = getLocalDateTime.now();
        final Book book = new Book(null, author, title, price, available, creationDate, updateDate);

        return registerBookGateway.execute(book);
    }

    private Author getAuthorAndValidate(final Long authorId) {
        final Optional<Author> optional = getAuthorByIdGateway.execute(authorId);
        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", String.valueOf(authorId)));
    }

    private void checkDuplicatedTitleViolation(final String title) {
        final Optional<Book> optional = getBookByTitleGateway.execute(title);
        optional.ifPresent(this::throwUniqueConstraintException);
    }

    private void throwUniqueConstraintException(final Book book) {
        throw new UniqueConstraintException("validation.book.title.already.exists", book.getTitle());
    }
}
