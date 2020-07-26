package io.github.jtsato.bookstore.entrypoint.rest.author.domain.response;

import java.util.List;

import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato  
 */

public class FindAuthorsByIdsResponse extends PageImpl<FindAuthorsByIdInnerResponse> {

    public FindAuthorsByIdsResponse(final List<FindAuthorsByIdInnerResponse> content, final Pageable pageable) {
        super(content, pageable);
    }
}
