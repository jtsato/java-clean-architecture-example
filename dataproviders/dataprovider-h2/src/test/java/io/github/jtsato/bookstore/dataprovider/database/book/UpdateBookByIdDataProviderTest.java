package io.github.jtsato.bookstore.dataprovider.database.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.database.author.GetAuthorByIdDataProvider;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({UpdateBookByIdDataProvider.class, GetAuthorByIdDataProvider.class})
@Sql("UpdateBookByIdDataProviderTest.sql")
class UpdateBookByIdDataProviderTest {

    @Autowired
    private UpdateBookByIdDataProvider updateBookByIdDataProvider;

    @Autowired
    private GetAuthorByIdDataProvider getAuthorByIdDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to update book by id if found")
    @Test
    void successfulToUpdateBookById() {

        final Book update = new Book(4L, "Effective Java (3rd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), BigDecimal.valueOf(50.55), getAuthor(2L));

        final Book result = updateBookById(update);

        assertThat(result.getId()).isEqualTo(update.getId());
        assertThat(result.getTitle()).isEqualTo(update.getTitle());
        assertThat(result.getCreationDate()).isEqualTo(update.getCreationDate());
        assertThat(result).isEqualToComparingFieldByField(update);

        assertThat(bookRepository.count()).isEqualTo(4L);
    }

    @DisplayName("Successful to update book by id with new Author")
    @Test
    void successfulToUpdateBookByIdWithNewAuthor() {

        final Book update = new Book(4L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), BigDecimal.valueOf(40.44), getAuthor(1L));

        final Book result = updateBookById(update);

        assertThat(result).isEqualToComparingFieldByField(update);

        assertThat(bookRepository.count()).isEqualTo(4L);
    }

    private Book updateBookById(final Book candidate) {

        final Optional<Book> optional = updateBookByIdDataProvider.updateBookById(candidate);

        assertThat(optional).isPresent();

        return optional.get();
    }

    private Author getAuthor(final Long authorId) {

        final Optional<Author> optional = getAuthorByIdDataProvider.getAuthorById(authorId);

        assertThat(optional).isPresent();

        return optional.get();
    }

    @DisplayName("Fail to update book by id if not found")
    @Test
    void failToUpdateBookByIdIfNotFound() {

        final Optional<Book> optional = updateBookByIdDataProvider.updateBookById(new Book(5L, null, null, null, getAuthor(2L)));

        assertThat(optional).isNotPresent();
    }
}
