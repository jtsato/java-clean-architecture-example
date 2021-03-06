package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;


/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Book By Id")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Testcontainers
@Import({GetBookByIdProvider.class})
@Sql("GetBookByIdProviderTest.sql")
class GetBookByIdProviderTest {

    @Autowired
    private GetBookByIdProvider getBookByIdProvider;

    @Autowired
    private BookRepository authorRepository;

    @DisplayName("Successful to get book by id if found")
    @Test
    void successfulToGetBookByIdIfFound() {

        final Optional<Book> optional = getBookByIdProvider.execute(1L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to get book by id if not found")
    @Test
    void failToGetBookByIdIfNotFound() {

        final Optional<Book> optional = getBookByIdProvider.execute(2L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isOne();
    }
}
