package io.github.jtsato.bookstore.dataprovider.author;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.common.PageMapper;
import io.github.jtsato.bookstore.dataprovider.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional(readOnly = true)
@Service
public class FindAuthorsByIdsDataProvider implements FindAuthorsByIdsGateway {

    @Autowired
    AuthorRepository authorRepository;

    private final PageMapper<Author, AuthorEntity> pageMapper = new PageMapper<>() {};

    @Override
    public Page<Author> findAuthorsByIds(final List<Long> ids) {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(0, 1000, "id:asc");

        final BooleanExpression predicate = QAuthorEntity.authorEntity.id.in(ids);
        final org.springframework.data.domain.Page<AuthorEntity> page = authorRepository.findAll(predicate, pageRequest);

        return pageMapper.of(page, AuthorMapper::of);
    }
}
