package io.github.jtsato.bookstore.infra.author;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class FindAuthorsByIdsProvider implements FindAuthorsByIdsGateway {

    @Override
    public Page<Author> execute(final List<Long> ids) {
        return new PageImpl<>(Collections.emptyList(), new Pageable(0, 0, 0, 0L, 0));
    }
}
