package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
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
    
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Book> execute(final Book book) {
        final Optional<BookEntity> optional = bookRepository.findById(book.getId());
        return optional.map(bookEntity -> updateBookEntity(bookEntity, book));
    }

    private Book updateBookEntity(final BookEntity bookEntity, final Book book) {
        updateAuthor(book, bookEntity);
        bookEntity.setTitle(book.getTitle());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setAvailable(book.getAvailable());
        bookEntity.setUpdateDate(book.getUpdateDate());
        return bookMapper.of(bookRepository.save(bookEntity));
    }

    private void updateAuthor(final Book book, final BookEntity bookEntity) {
        final Long currentAuthorId = bookEntity.getAuthor().getId();
        final Long newAuthorId = book.getAuthor().getId();
        if (!newAuthorId.equals(currentAuthorId)) {
            authorRepository.findById(newAuthorId).ifPresent(bookEntity::setAuthor);
        }
    }
}
