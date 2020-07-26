package io.github.jtsato.bookstore.dataprovider.database.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.database.author.GetAuthorByIdDataProvider;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({RegisterBookDataProvider.class, GetAuthorByIdDataProvider.class})
@Sql("GetAuthorByIdDataProviderTest.sql")
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

        final Book book = registerBookDataProvider.registerBook(new Book(null, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author));

        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getAuthor()).isEqualTo(author);

        assertThat(bookRepository.count()).isOne();
    }

    private Author getAuthor() {

        final Optional<Author> optional = getAuthorByIdDataProvider.getAuthorById(1L);

        assertThat(optional).isPresent();

        return optional.get();
    }

    @DisplayName("Fail to register book if parameters are not valid")
    @Test
    void failToRegisterBookIfParametersAreNotValid() {

        final Book book = new Book(null, null, null, getAuthor());
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> registerBookDataProvider.registerBook(book));

        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class);
    }
}
