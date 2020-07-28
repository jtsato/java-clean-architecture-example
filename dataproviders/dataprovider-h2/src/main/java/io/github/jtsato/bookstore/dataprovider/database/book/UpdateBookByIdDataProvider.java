package io.github.jtsato.bookstore.dataprovider.database.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.database.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class UpdateBookByIdDataProvider implements UpdateBookByIdGateway {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Book> updateBookById(final Book book) {

        final Optional<BookEntity> optional = bookRepository.findById(book.getId());

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        final BookEntity bookEntity = optional.get();

        checkAndUpdateAuthor(bookEntity, book.getAuthor().getId());

        return Optional.of(BookMapper.of(bookRepository.saveAndFlush(updateBookEntity(bookEntity, book))));
    }

    private void checkAndUpdateAuthor(final BookEntity bookEntity, final Long authorId) {

        if (authorId.equals(bookEntity.getAuthor().getId())) {
            return;
        }

        final Optional<AuthorEntity> optional = authorRepository.findById(authorId);
        bookEntity.setAuthor(optional.orElseGet(bookEntity::getAuthor));
    }

    private BookEntity updateBookEntity(final BookEntity bookEntity, final Book book) {
        bookEntity.setTitle(book.getTitle());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setAvailable(book.getAvailable());
        
        return bookEntity;
    }
}
