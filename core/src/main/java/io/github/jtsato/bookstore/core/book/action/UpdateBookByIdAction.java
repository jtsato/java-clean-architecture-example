package io.github.jtsato.bookstore.core.book.action;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

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

@Named
@RequiredArgsConstructor
public class UpdateBookByIdAction implements UpdateBookByIdUseCase {

    private final UpdateBookByIdGateway updateBookGateway;

    private final GetAuthorByIdGateway getAuthorByIdGateway;

    private final GetBookByTitleGateway getBookByTitleGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public Book execute(final UpdateBookByIdParameters parameters) {

        final Author author = getAuthorAndValidate(parameters.getAuthorId());

        checkDuplicatedTitleViolation(parameters.getId(), parameters.getTitle());

        final Long id = parameters.getId();
        final String title = StringUtils.stripToEmpty(parameters.getTitle());
        final BigDecimal price = parameters.getPrice();
        final Boolean available = parameters.getAvailable();
        final LocalDateTime updateDate = getLocalDateTime.now();
        final Book book = new Book(id, author, title, price, available, null, updateDate);

        final Optional<Book> optional = updateBookGateway.execute(book);
        return optional.orElseThrow(() -> new NotFoundException("validation.book.id.notfound", String.valueOf(parameters.getId())));
    }

    private Author getAuthorAndValidate(final Long authorId) {
        final Optional<Author> optional = getAuthorByIdGateway.execute(authorId);
        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", String.valueOf(authorId)));
    }

    private void checkDuplicatedTitleViolation(final Long bookId, final String bookTitle) {

        final Optional<Book> optional = getBookByTitleGateway.execute(bookTitle);

        if (optional.isEmpty()) {
            return;
        }

        final Book book = optional.get();
        if (!book.getId().equals(bookId)) {
            throw new UniqueConstraintException("validation.book.title.already.exists", book.getTitle());
        }
    }
}
