package io.github.jtsato.bookstore.core.book.usecase.impl;

import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookContentGateway;
import io.github.jtsato.bookstore.core.book.usecase.RegisterBookContentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookContentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
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
public class RegisterBookContentUseCaseImpl implements RegisterBookContentUseCase {

    private final RegisterBookContentGateway registerBookContentGateway;

    private final GetLocalDateTime getLocalDateTime;

    @Override
    public BookContent registerBookContent(final RegisterBookContentParameters parameters) {
        
    	final BookContent bookContent = new BookContent(null, parameters.getBookId(), parameters.getContent(), getLocalDateTime.now(), getLocalDateTime.now());
        
    	return registerBookContentGateway.registerBookContent(bookContent);
    }
}
