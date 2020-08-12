package io.github.jtsato.bookstore.entrypoint.rest.book.domain.response;

import java.util.List;

import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

public class SearchBooksResponse extends PageImpl<SearchBooksInnerResponse> {

    public SearchBooksResponse(final List<SearchBooksInnerResponse> content, final Pageable pageable) {
        super(content, pageable);
    }
}
