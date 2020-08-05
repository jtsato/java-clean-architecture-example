package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.Optional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookDocumentByBookIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookDocumentGateway;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookDocumentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
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

@RequiredArgsConstructor
public class SaveBookDocumentUseCaseImpl implements SaveBookDocumentUseCase {

    private final SaveBookDocumentGateway saveBookDocumentGateway;

    private final GetBookByIdGateway getBookByIdGateway;

    private final GetBookDocumentByBookIdGateway getBookDocumentByBookIdGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public BookDocument saveBookDocument(final SaveBookDocumentParameters parameters) {

        final Long bookId = parameters.getBookId();

        checkIfBookExists(bookId);

        final Optional<BookDocument> optional = getBookDocumentByBookIdGateway.getBookDocumentByBookId(bookId);

        if (optional.isPresent()) {
            final BookDocument bookDocument = optional.get();
            bookDocument.setContent(parameters.getContent());
            bookDocument.setUpdateDate(getLocalDateTime.now());
            return saveBookDocumentGateway.saveBookDocument(bookDocument);
        }

        final BookDocument bookDocument = new BookDocument(null,
                                                           bookId,
                                                           parameters.getContentType(),
                                                           parameters.getExtension(),
                                                           parameters.getName(),
                                                           parameters.getSize(),
                                                           parameters.getContent(),
                                                           getLocalDateTime.now(),
                                                           getLocalDateTime.now());

        return saveBookDocumentGateway.saveBookDocument(bookDocument);
    }

    private void checkIfBookExists(final Long bookId) {

        final Optional<Book> optional = getBookByIdGateway.getBookById(bookId);

        if (!optional.isPresent()) {
            throw new NotFoundException("validation.book.id.notfound", bookId);
        }
    }
}