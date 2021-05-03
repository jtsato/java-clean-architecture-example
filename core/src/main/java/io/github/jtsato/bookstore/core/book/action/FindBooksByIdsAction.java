package io.github.jtsato.bookstore.core.book.action;

import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;

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

@Named
@RequiredArgsConstructor
public class FindBooksByIdsAction implements FindBooksByIdsUseCase {

    private final FindBooksByIdsGateway findBooksByIdsGateway;

    @Override
    public Page<Book> execute(final List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            throw new InvalidParameterException("validation.books.ids.null");
        }

        if (ids.size() > 1000) {
            throw new InvalidParameterException("validation.get.by.ids.limit");
        }

        return findBooksByIdsGateway.execute(ids);
    }
}
