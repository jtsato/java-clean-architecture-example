package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.FindBooksByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class FindBooksByIdsDataProvider implements FindBooksByIdsGateway {

    @Override
    public Page<Book> execute(final List<Long> ids) {
        return new PageImpl<>(Collections.emptyList(), new Pageable(0, 0, 0, 0L, 0));
    }
}
