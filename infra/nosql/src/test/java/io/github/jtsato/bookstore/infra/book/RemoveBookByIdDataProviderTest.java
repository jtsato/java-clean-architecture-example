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

@DisplayName("Remove Book By Id")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({RemoveBookByIdProvider.class})
class RemoveBookByIdProviderTest {

    @Autowired
    private RemoveBookByIdProvider removeBookByIdProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to remove author by id if found")
    @Test
    void successfulToRemoveBookByIdIfFound() {

        final Optional<Book> optional = removeBookByIdProvider.execute(4L);

        assertThat(optional).isPresent();
        assertThat(bookRepository.count()).isEqualTo(3);
    }

    @DisplayName("Fail to remove author by id if not found")
    @Test
    void failToRemoveBookByIdIfNotFound() {

        final Optional<Book> optional = removeBookByIdProvider.execute(5L);

        assertThat(optional).isNotPresent();
        assertThat(bookRepository.count()).isEqualTo(4);
    }
}
