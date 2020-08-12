package io.github.jtsato.bookstore.dataprovider.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DataJpaTest
@Import({FindBooksByIdsDataProvider.class})
@Sql("FindBooksByIdsDataProviderTest.sql")
class FindBooksByIdsDataProviderTest {

    @Autowired
    private FindBooksByIdsDataProvider findBooksByIdsDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to find books by IDs if found")
    @Test
    void successfulToFindBooksByIdsIfFound() {

        final List<Long> ids = Arrays.asList(1L, 2L);

        final Page<Book> pageOfBooks = findBooksByIdsDataProvider.findBooksByIds(ids);

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

        final Page<Book> pageOfBooks = findBooksByIdsDataProvider.findBooksByIds(ids);

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
