package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.infra.author.GetAuthorByIdProvider;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Update Book By Id")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({UpdateBookByIdProvider.class, GetAuthorByIdProvider.class})
@Sql("UpdateBookByIdProviderTest.sql")
class UpdateBookByIdProviderTest {

    @Autowired
    private UpdateBookByIdProvider updateBookByIdProvider;

    @Autowired
    private GetAuthorByIdProvider getAuthorByIdProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to update book by id if found")
    @Test
    void successfulToUpdateBookById() {

        final Book update = new Book(4L,
                                     getAuthor(2L),
                                     "Effective Java (3rd Edition)",
                                     BigDecimal.valueOf(50.55),
                                     Boolean.TRUE,
                                     null,
                                     LocalDateTime.parse("2020-04-13T22:04:59.123"));

        final Book result = updateBookById(update);

        assertThat(result.getAuthor()).isEqualTo(update.getAuthor());

        assertThat(result.getId()).isEqualTo(update.getId());
        assertThat(result.getTitle()).isEqualTo(update.getTitle());
        assertThat(result.getPrice()).isEqualTo(update.getPrice());
        assertThat(result.getAvailable()).isEqualTo(update.getAvailable());
        assertThat(result.getUpdateDate()).isEqualTo(update.getUpdateDate());

        assertThat(bookRepository.count()).isEqualTo(4L);
    }

    @DisplayName("Successful to update book by id with new Author")
    @Test
    void successfulToUpdateBookByIdWithNewAuthor() {

        final Book update = new Book(4L,
                                     getAuthor(1L),
                                     "Effective Java (2nd Edition)",
                                     BigDecimal.valueOf(40.44),
                                     Boolean.TRUE,
                                     null,
                                     LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final Book result = updateBookById(update);

        assertThat(result.getAuthor()).isEqualTo(update.getAuthor());

        assertThat(result.getId()).isEqualTo(update.getId());
        assertThat(result.getTitle()).isEqualTo(update.getTitle());
        assertThat(result.getPrice()).isEqualTo(update.getPrice());
        assertThat(result.getAvailable()).isEqualTo(update.getAvailable());
        assertThat(result.getUpdateDate()).isEqualTo(update.getUpdateDate());

        assertThat(bookRepository.count()).isEqualTo(4L);
    }

    private Book updateBookById(final Book candidate) {

        final Optional<Book> optional = updateBookByIdProvider.execute(candidate);

        assertThat(optional).isPresent();

        return optional.get();
    }

    private Author getAuthor(final Long authorId) {

        final Optional<Author> optional = getAuthorByIdProvider.execute(authorId);

        assertThat(optional).isPresent();

        return optional.get();
    }

    @DisplayName("Fail to update book by id if book not found")
    @Test
    void failToUpdateBookByIdIfBookNotFound() {

        final Book update = new Book(5L, getAuthor(2L), null, null, null, null, null);

        final Optional<Book> optional = updateBookByIdProvider.execute(update);

        assertThat(optional).isNotPresent();
    }
}
