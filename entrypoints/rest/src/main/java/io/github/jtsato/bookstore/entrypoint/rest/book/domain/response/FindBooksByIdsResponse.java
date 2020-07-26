package io.github.jtsato.bookstore.entrypoint.rest.book.domain.response;

import java.util.List;

import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato  
 */

public class FindBooksByIdsResponse extends PageImpl<FindBooksByIdsInnerResponse> {

    public FindBooksByIdsResponse(final List<FindBooksByIdsInnerResponse> content, final Pageable pageable) {
        super(content, pageable);
    }
}
