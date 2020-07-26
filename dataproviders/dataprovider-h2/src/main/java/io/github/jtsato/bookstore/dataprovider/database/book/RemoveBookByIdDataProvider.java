package io.github.jtsato.bookstore.dataprovider.database.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RemoveBookByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.database.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato 
 */

@Transactional
@Service
public class RemoveBookByIdDataProvider implements RemoveBookByIdGateway {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Optional<Book> removeBookById(final Long id) {

        final Optional<BookEntity> optional = bookRepository.findById(id);

        if (optional.isPresent()) {
            final BookEntity bookEntity = optional.get();
            final Optional<Book> optionalOfBook = Optional.of(BookMapper.of(bookEntity));
            bookRepository.delete(bookEntity);
            return optionalOfBook;
        }

        return Optional.empty();
    }
}
