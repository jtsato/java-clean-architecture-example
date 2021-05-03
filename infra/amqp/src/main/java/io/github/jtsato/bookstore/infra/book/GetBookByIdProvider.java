package io.github.jtsato.bookstore.infra.book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class GetBookByIdProvider implements GetBookByIdGateway {

    @Override
    public Optional<Book> execute(final Long id) {
        return Optional.of(new Book(id,
                                    new Author(1L, null, Gender.FEMALE, LocalDate.MIN),
                                    null,
                                    null,
                                    Boolean.FALSE,
                                    LocalDateTime.now(),
                                    LocalDateTime.now()));
    }
}
