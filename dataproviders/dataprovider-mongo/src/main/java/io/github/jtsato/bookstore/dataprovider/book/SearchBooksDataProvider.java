package io.github.jtsato.bookstore.dataprovider.book;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.domain.QBookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;
import io.github.jtsato.bookstore.dataprovider.book.repository.SearchBooksPredicateBuilder;
import io.github.jtsato.bookstore.dataprovider.common.PageMapper;
import io.github.jtsato.bookstore.dataprovider.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class SearchBooksDataProvider implements SearchBooksGateway {
    
    @Autowired
    BookRepository bookRepository;

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    private final PageMapper<Book, BookEntity> pageMapper = new PageMapper<>() {};

    @Override
    public Page<Book> execute(final SearchBooksParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(pageNumber, size, sanitizeOrderBy(orderBy));
        final BooleanBuilder predicate = new SearchBooksPredicateBuilder(QBookEntity.bookEntity).buildBooleanBuilder(parameters);

        final org.springframework.data.domain.Page<BookEntity> page = bookRepository.findAll(predicate, pageRequest);

        return pageMapper.of(page, bookMapper::of);
    }

    private String sanitizeOrderBy(final String orderBy) {
        if (StringUtils.isBlank(orderBy) || StringUtils.equalsIgnoreCase(orderBy, "UNSORTED")) {
            return "title:asc";
        }
        return StringUtils.stripToEmpty(orderBy);
    }
}
