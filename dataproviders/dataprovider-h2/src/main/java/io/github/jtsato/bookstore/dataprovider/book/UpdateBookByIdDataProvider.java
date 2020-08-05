package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;

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
        
        updateAuthor(book, bookEntity);
        
        return Optional.of(BookMapper.of(bookRepository.saveAndFlush(updateBookEntity(bookEntity, book))));
    }

    private void updateAuthor(final Book book, final BookEntity bookEntity) {
        final Long currentAuthorId = bookEntity.getAuthor().getId();
        final Long newAuthorId = book.getAuthor().getId();
        if (!newAuthorId.equals(currentAuthorId)) {
            authorRepository.findById(newAuthorId).ifPresent(bookEntity::setAuthor);
        }
    }

    private BookEntity updateBookEntity(final BookEntity bookEntity, final Book book) {
        bookEntity.setTitle(book.getTitle());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setAvailable(book.getAvailable());
        return bookEntity;
    }
}
