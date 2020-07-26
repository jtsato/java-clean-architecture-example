package io.github.jtsato.bookstore.dataprovider.database.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.database.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class RegisterBookDataProvider implements RegisterBookGateway {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book registerBook(final Book book) {
        final BookEntity bookEntity = BookMapper.of(book);
        return BookMapper.of(bookRepository.saveAndFlush(bookEntity));
    }
}
