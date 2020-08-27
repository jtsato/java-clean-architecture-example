package io.github.jtsato.bookstore.dataprovider.book;

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
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;
import io.github.jtsato.bookstore.dataprovider.common.ContainersContextConfiguration;


/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Book By Id")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Testcontainers
@Import({GetBookByIdDataProvider.class})
@Sql("GetBookByIdDataProviderTest.sql")
class GetBookByIdDataProviderTest extends ContainersContextConfiguration {

    @Autowired
    private GetBookByIdDataProvider getBookByIdDataProvider;

    @Autowired
    private BookRepository authorRepository;

    @DisplayName("Successful to get book by id if found")
    @Test
    void successfulToGetBookByIdIfFound() {

        final Optional<Book> optional = getBookByIdDataProvider.execute(1L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to get book by id if not found")
    @Test
    void failToGetBookByIdIfNotFound() {

        final Optional<Book> optional = getBookByIdDataProvider.execute(2L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isOne();
    }
}
