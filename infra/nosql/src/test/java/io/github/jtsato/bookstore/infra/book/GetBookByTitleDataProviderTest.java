package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Book By Title")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import({GetBookByTitleProvider.class})
class GetBookByTitleProviderTest {

    @Autowired
    private GetBookByTitleProvider getBookByTitleProvider;

    @Autowired
    private BookRepository authorRepository;

    @DisplayName("Successful to get book by title if found")
    @Test
    void successfulToGetBookByTitleIfFound() {

        final Optional<Book> optional = getBookByTitleProvider.execute("Effective Java");

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }

    @DisplayName("Fail to get book by title if not found")
    @Test
    void failToGetAuthorByTitleIfNotFound() {

        final Optional<Book> optional = getBookByTitleProvider.execute("Effective Java (2nd Edition)");

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }
}
