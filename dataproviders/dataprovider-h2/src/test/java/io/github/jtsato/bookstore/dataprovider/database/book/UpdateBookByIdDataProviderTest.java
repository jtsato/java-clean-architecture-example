package io.github.jtsato.bookstore.dataprovider.database.book;

import static org.assertj.core.api.Assertions.assertThat;

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

        final Author author = getAuthor(2L);

        final Book book1 = new Book(4L, "Effective Java (3rd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author);

        final Book book2 = updateBookById(book1);

        assertThat(book2.getId()).isEqualTo(book1.getId());
        assertThat(book2.getTitle()).isEqualTo(book1.getTitle());
        assertThat(book2.getCreationDate()).isEqualTo(book1.getCreationDate());
        assertThat(book2).isEqualToComparingFieldByField(book1);

        assertThat(bookRepository.count()).isEqualTo(4L);
    }

    @DisplayName("Successful to update book by id with new Author")
    @Test
    void successfulToUpdateBookByIdWithNewAuthor() {

        final Author author = getAuthor(1L);

        final Book book1 = new Book(4L, "Effective Java (3rd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author);

        final Book book2 = updateBookById(book1);

        assertThat(book2).isEqualToComparingFieldByField(book1);

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

        final Optional<Book> optional = updateBookByIdDataProvider.updateBookById(new Book(5L, null, null, getAuthor(2L)));

        assertThat(optional).isNotPresent();
    }
}
