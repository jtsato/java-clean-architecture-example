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

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;
import io.github.jtsato.bookstore.dataprovider.common.ContainersContextConfiguration;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Book By Title")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({GetBookByTitleDataProvider.class})
@Sql("GetBookByTitleDataProviderTest.sql")
class GetBookByTitleDataProviderTest extends ContainersContextConfiguration {

    @Autowired
    private GetBookByTitleDataProvider getBookByTitleDataProvider;

    @Autowired
    private BookRepository authorRepository;

    @DisplayName("Successful to get book by title if found")
    @Test
    void successfulToGetBookByTitleIfFound() {

        final Optional<Book> optional = getBookByTitleDataProvider.execute("Effective Java");

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }

    @DisplayName("Fail to get book by title if not found")
    @Test
    void failToGetAuthorByTitleIfNotFound() {

        final Optional<Book> optional = getBookByTitleDataProvider.execute("Effective Java (2nd Edition)");

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }
}
