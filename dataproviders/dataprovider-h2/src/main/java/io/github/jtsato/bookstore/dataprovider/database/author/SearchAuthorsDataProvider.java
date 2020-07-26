package io.github.jtsato.bookstore.dataprovider.database.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorPredicateBuilder;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.database.common.PageMapper;
import io.github.jtsato.bookstore.dataprovider.database.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional(readOnly = true)
@Service
public class SearchAuthorsDataProvider implements SearchAuthorsGateway {

    @Autowired
    AuthorRepository authorRepository;

    private final PageMapper<Author, AuthorEntity> pageMapper = new PageMapper<Author, AuthorEntity>(){};
    
    public Page<Author> searchAuthors(final SearchAuthorsParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(pageNumber, size, orderBy);

        final BooleanBuilder predicate = new AuthorPredicateBuilder(QAuthorEntity.authorEntity).buildBooleanBuilder(parameters);
        
        final org.springframework.data.domain.Page<AuthorEntity> page = authorRepository.findAll(predicate, pageRequest);
        
        return pageMapper.of(page, AuthorMapper::of);
    }
}
