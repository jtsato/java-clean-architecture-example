package io.github.jtsato.bookstore.dataprovider.rabbitmq.author;

import java.util.Collections;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class SearchAuthorsDataProvider implements SearchAuthorsGateway {

    public Page<Author> searchAuthors(final SearchAuthorsParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {
        return new PageImpl<>(Collections.emptyList(), new Pageable(pageNumber, size, 0, 0L, 0));
    }
}
