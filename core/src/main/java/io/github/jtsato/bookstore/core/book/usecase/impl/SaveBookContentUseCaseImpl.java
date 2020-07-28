package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.Optional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookContentByBookIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookContentGateway;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookContentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookContentParameters;
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
public class SaveBookContentUseCaseImpl implements SaveBookContentUseCase {

    private final SaveBookContentGateway saveBookContentGateway;
    
    private final GetBookByIdGateway getBookByIdGateway;
    
    private final GetBookContentByBookIdGateway getBookContentByBookIdGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public BookContent saveBookContent(final SaveBookContentParameters parameters) {
    	
    	final Long bookId = parameters.getBookId();
    	
    	checkIfBookExists(bookId);
    	
    	final Optional<BookContent> optional = getBookContentByBookIdGateway.getBookContentByBookId(bookId);
    	
    	if (optional.isPresent()) {
    		final BookContent bookContent = optional.get();
			bookContent.setContent(parameters.getContent());
    		bookContent.setUpdateDate(getLocalDateTime.now());
    		return saveBookContentGateway.saveBookContent(bookContent);
    	}
    	
    	final BookContent bookContent = new BookContent(null, bookId, parameters.getContent(), getLocalDateTime.now(), getLocalDateTime.now());
    	return saveBookContentGateway.saveBookContent(bookContent);
    }

	private void checkIfBookExists(final Long bookId) {
		
		final Optional<Book> optional = getBookByIdGateway.getBookById(bookId);
		
		if (!optional.isPresent()) {
			throw new NotFoundException("validation.book.id.notfound", bookId);
		}
	}
}
