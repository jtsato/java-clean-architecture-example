package io.github.jtsato.bookstore.infra.book;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.infra.book.domain.BookEntity;
import io.github.jtsato.bookstore.infra.book.mapper.BookMapper;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
@RequiredArgsConstructor
public class RegisterBookProvider implements RegisterBookGateway {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    
    private final BookRepository bookRepository;

    @Override
    public Book execute(final Book book) {
        final BookEntity bookEntity = bookMapper.of(book);
        return bookMapper.of(bookRepository.saveAndFlush(bookEntity));
    }
}
