package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class FindAuthorsByIdsDataProvider implements FindAuthorsByIdsGateway {

    @Override
    public Page<Author> findAuthorsByIds(final List<Long> ids) {
        return new PageImpl<>(Collections.emptyList(), new Pageable(0, 0, 0, 0L, 0));
    }
}
