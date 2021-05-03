package io.github.jtsato.bookstore.infra.book;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RemoveBookByIdGateway;
import io.github.jtsato.bookstore.infra.book.domain.BookEntity;
import io.github.jtsato.bookstore.infra.book.mapper.BookMapper;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato 
 */

@Transactional
@Service
public class RemoveBookByIdDataProvider implements RemoveBookByIdGateway {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    
    @Autowired
    BookRepository bookRepository;

    @Override
    public Optional<Book> execute(final Long id) {
        final Optional<BookEntity> optional = bookRepository.findById(id);
        return optional.map(this::removeBookEntity);
    }

    private Book removeBookEntity(final BookEntity bookEntity) {
        final Book book = bookMapper.of(bookEntity);
        bookRepository.delete(bookEntity);
        return book;
    }
}
