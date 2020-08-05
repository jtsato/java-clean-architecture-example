package io.github.jtsato.bookstore.core.book.usecase.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.FindBooksByIdsGateway;
import io.github.jtsato.bookstore.core.book.usecase.FindBooksByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
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
public class FindBooksByIdsUseCaseImpl implements FindBooksByIdsUseCase {

    private final FindBooksByIdsGateway findBooksByIdsGateway;

    @Override
    public Page<Book> findBooksByIds(final List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            throw new InvalidParameterException("validation.books.ids.null");
        }

        if (ids.size() > 1000) {
            throw new InvalidParameterException("validation.get.by.ids.limit");
        }

        return findBooksByIdsGateway.findBooksByIds(ids);
    }
}
