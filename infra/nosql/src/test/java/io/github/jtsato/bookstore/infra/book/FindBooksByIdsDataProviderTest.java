package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Find Books By Ids")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import({FindBooksByIdsDataProvider.class})
class FindBooksByIdsDataProviderTest {

    @Autowired
    private FindBooksByIdsDataProvider findBooksByIdsDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to find books by IDs if found")
    @Test
    void successfulToFindBooksByIdsIfFound() {

        final List<Long> ids = Arrays.asList(1L, 2L);

        final Page<Book> pageOfBooks = findBooksByIdsDataProvider.execute(ids);

        assertThat(pageOfBooks).isNotNull();

        final List<Book> Books = pageOfBooks.getContent();

        assertThat(Books).isNotNull().isNotEmpty();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(1000);
        assertThat(pageable.getNumberOfElements()).isEqualTo(2);
        assertThat(pageable.getTotalOfElements()).isEqualTo(2);
        assertThat(pageable.getTotalPages()).isOne();

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Fail to find books by IDs if not found")
    @Test
    void successfulToFindBooksByIdsIfNotFound() {

        final List<Long> ids = Arrays.asList(5L, 6L);

        final Page<Book> pageOfBooks = findBooksByIdsDataProvider.execute(ids);

        assertThat(pageOfBooks).isNotNull();

        final List<Book> Books = pageOfBooks.getContent();

        assertThat(Books).isNotNull().isEmpty();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(1000);
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();

        assertThat(bookRepository.count()).isEqualTo(4);
    }
}
