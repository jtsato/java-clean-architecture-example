package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.infra.author.GetAuthorByIdDataProvider;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Register Book")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({RegisterBookDataProvider.class, GetAuthorByIdDataProvider.class})
@Sql("RegisterBookDataProviderTest.sql")
class RegisterBookDataProviderTest {

    @Autowired
    private RegisterBookDataProvider registerBookDataProvider;

    @Autowired
    private GetAuthorByIdDataProvider getAuthorByIdDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to register book if parameters are valid")
    @Test
    void successfulToRegisterBookIfParametersAreValid() {

        final Author author = getAuthor();

        final Book newBook = new Book(null,
                                      author,
                                      "Effective Java (2nd Edition)",
                                      BigDecimal.valueOf(10.00),
                                      Boolean.TRUE,
                                      LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                      LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final Book result = registerBookDataProvider.execute(newBook);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(newBook.getTitle());
        assertThat(result.getCreationDate()).isEqualTo(newBook.getCreationDate());
        assertThat(result.getUpdateDate()).isEqualTo(newBook.getUpdateDate());
        assertThat(result.getAuthor()).isEqualTo(newBook.getAuthor());

        assertThat(bookRepository.count()).isOne();
    }

    private Author getAuthor() {

        final Optional<Author> optional = getAuthorByIdDataProvider.execute(1L);

        assertThat(optional).isPresent();

        return optional.get();
    }

    @DisplayName("Fail to register book if parameters are not valid")
    @Test
    void failToRegisterBookIfParametersAreNotValid() {

        final Book book = new Book(null, getAuthor(), null, null, null, null, null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> registerBookDataProvider.execute(book));

        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class);
    }
}
