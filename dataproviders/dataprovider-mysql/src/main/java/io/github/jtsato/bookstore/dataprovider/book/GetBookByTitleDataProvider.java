package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByTitleGateway;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class GetBookByTitleDataProvider implements GetBookByTitleGateway {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Optional<Book> execute(final String title) {
        final Optional<BookEntity> optional = bookRepository.findByTitle(title);
        return optional.map(BookMapper::of);
    }
}
