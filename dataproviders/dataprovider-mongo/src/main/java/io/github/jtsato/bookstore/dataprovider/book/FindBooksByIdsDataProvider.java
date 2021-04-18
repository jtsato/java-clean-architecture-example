package io.github.jtsato.bookstore.dataprovider.book;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.FindBooksByIdsGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.domain.QBookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;
import io.github.jtsato.bookstore.dataprovider.common.PageMapper;
import io.github.jtsato.bookstore.dataprovider.common.PageRequestHelper;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class FindBooksByIdsDataProvider implements FindBooksByIdsGateway {

    private final PageMapper<Book, BookEntity> pageMapper = new PageMapper<>() {};
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Autowired
    BookRepository bookRepository;

    @Override
    public Page<Book> execute(final List<Long> ids) {
    	
        final BooleanExpression predicate = QBookEntity.bookEntity.bookId.in(ids);
        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(0, 1000, "id:asc");

        final org.springframework.data.domain.Page<BookEntity> page = bookRepository.findAll(predicate, pageRequest);

        return pageMapper.of(page, bookMapper::of);
    }
}
