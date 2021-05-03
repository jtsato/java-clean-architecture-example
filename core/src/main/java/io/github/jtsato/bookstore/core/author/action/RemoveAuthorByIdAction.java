package io.github.jtsato.bookstore.core.author.action;

import java.util.Optional;

import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.RemoveAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksByAuthorIdGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.ParentConstraintException;
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
public class RemoveAuthorByIdAction implements RemoveAuthorByIdUseCase {

    private final RemoveAuthorByIdGateway removeAuthorByIdGateway;

    private final SearchBooksByAuthorIdGateway searchBooksByAuthorIdGateway;

    @Override
    public Author execute(final Long id) {

        if (id == null) {
            throw new InvalidParameterException("validation.author.id.null");
        }

        avoidRemovalOfAuthorsWithBooks(id);

        final Optional<Author> optional = removeAuthorByIdGateway.execute(id);

        return optional.orElseThrow(() -> new NotFoundException("validation.author.id.notfound", String.valueOf(id)));
    }

    private void avoidRemovalOfAuthorsWithBooks(final Long authorId) {

        final Page<Book> pageOfBook = searchBooksByAuthorIdGateway.execute(authorId, 0, 1, "id:asc");

        if (CollectionUtils.isNotEmpty(pageOfBook.getContent())) {
            throw new ParentConstraintException("validation.author.with.books.removal");
        }
    }
}
