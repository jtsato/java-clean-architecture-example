package io.github.jtsato.bookstore.infra.author;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.infra.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.infra.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.infra.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.infra.common.PageMapper;
import io.github.jtsato.bookstore.infra.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class FindAuthorsByIdsDataProvider implements FindAuthorsByIdsGateway {

    private final PageMapper<Author, AuthorEntity> pageMapper = new PageMapper<>() {};
    
    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Page<Author> execute(final List<Long> ids) {

        final BooleanExpression predicate = QAuthorEntity.authorEntity.id.in(ids);
        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(0, 1000, "id:asc");

        final org.springframework.data.domain.Page<AuthorEntity> page = authorRepository.findAll(predicate, pageRequest);

        return pageMapper.of(page, authorMapper::of);
    }
}
