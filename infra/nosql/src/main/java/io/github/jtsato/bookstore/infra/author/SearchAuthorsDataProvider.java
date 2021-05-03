package io.github.jtsato.bookstore.infra.author;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.infra.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.infra.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.infra.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.infra.author.repository.SearchAuthorsPredicateBuilder;
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.infra.common.PageMapper;
import io.github.jtsato.bookstore.infra.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class SearchAuthorsDataProvider implements SearchAuthorsGateway {

    private final PageMapper<Author, AuthorEntity> pageMapper = new PageMapper<>() {};

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Page<Author> execute(final SearchAuthorsParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(pageNumber, size, sanitizeOrderBy(orderBy));
        final BooleanBuilder predicate = new SearchAuthorsPredicateBuilder(QAuthorEntity.authorEntity).buildBooleanBuilder(parameters);

        final org.springframework.data.domain.Page<AuthorEntity> page = authorRepository.findAll(predicate, pageRequest);

        return pageMapper.of(page, authorMapper::of);
    }

    private String sanitizeOrderBy(final String orderBy) {
        if (StringUtils.isBlank(orderBy) || StringUtils.equalsIgnoreCase(orderBy, "UNSORTED")) {
            return "name:asc";
        }
        return StringUtils.stripToEmpty(orderBy);
    }
}
