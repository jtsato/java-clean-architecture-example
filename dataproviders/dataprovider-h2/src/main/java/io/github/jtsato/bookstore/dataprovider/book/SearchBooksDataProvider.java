package io.github.jtsato.bookstore.dataprovider.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;
import com.querydsl.core.BooleanBuilder;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.domain.QBookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookPredicateBuilder;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;
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

    private final PageMapper<Book, BookEntity> pageMapper = new PageMapper<>() {};

    @Override
    public Page<Book> searchBooks(final SearchBooksParameters parameters, final Integer pageNumber, final Integer size, final String orderBy) {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(pageNumber, size, orderBy);

        final BooleanBuilder predicate = new BookPredicateBuilder(QBookEntity.bookEntity).buildBooleanBuilder(parameters);

        final org.springframework.data.domain.Page<BookEntity> page = bookRepository.findAll(predicate,
                                                                                             pageRequest,
                                                                                             EntityGraphUtils.fromAttributePaths("author"));

        return pageMapper.of(page, BookMapper::of);
    }
}
