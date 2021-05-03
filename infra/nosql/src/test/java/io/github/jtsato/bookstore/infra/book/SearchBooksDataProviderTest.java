package io.github.jtsato.bookstore.infra.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Search Books")
@DataMongoTest
@ExtendWith(SpringExtension.class)
@Import({SearchBooksDataProvider.class})
class SearchBooksDataProviderTest {

    @Autowired
    private SearchBooksDataProvider searchBooksDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to search books if found")
    @Test
    void successfulToSearchBooksIfFound() {

        final Long authorId = 1L;
        final SearchAuthorsParameters searchAuthorsParameters = new SearchAuthorsParameters(authorId, null, null, null, null);

        final String title = "Core Java SE 9 for the Impatient, 2nd Edition";
        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(BigDecimal.valueOf(20.00), BigDecimal.valueOf(21.00));
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>("2020-03-12T21:04:59.000", "2020-03-13T21:05:00.000");
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>("2020-04-12T21:04:59.000", "2020-04-12T21:05:00.000");

        final SearchBooksParameters parameters = new SearchBooksParameters(searchAuthorsParameters, title, prices, Boolean.FALSE, creationDates, updateDates);

        final Page<Book> page = searchBooksDataProvider.execute(parameters, null, null, null);

        assertThat(page).isNotNull();

        final List<Book> books = page.getContent();
        
        assertThat(books).isNotNull().isNotEmpty();

        final Pageable pageable = page.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to search books if not found")
    @Test
    void successfulToSearchBooksIfNotFound() {

        final Long authorId = 1L;
        final SearchAuthorsParameters searchAuthorsParameters = new SearchAuthorsParameters(authorId, null, null, null, null);

        final String title = "Core Java SE 9 for the Impatient, 2nd Edition";
        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(BigDecimal.valueOf(20.00), BigDecimal.valueOf(21.00));
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>("2020-03-12T21:05:00", "2020-03-12T21:04:58");
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>("2020-03-12T21:04:59.000", "2020-03-12T21:05:00.000");

        final SearchBooksParameters parameters = new SearchBooksParameters(searchAuthorsParameters, title, prices, null, creationDates, updateDates);

        final String orderBy = "creationDate,title:desc";

        final Page<Book> page = searchBooksDataProvider.execute(parameters, 0, -1, orderBy);

        assertThat(page).isNotNull();

        final List<Book> books = page.getContent();

        assertThat(books).isNotNull().isEmpty();

        final Pageable pageable = page.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to paging books if found with unsorted order")
    @Test
    void successfulToPagingBooksIfFoundWithUnsortedOrder() {

        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>(null, null);

        final SearchBooksParameters parameters = new SearchBooksParameters(null, null, prices, null, creationDates, updateDates);
        final String orderBy = "UNSORTED";

        final Page<Book> page = searchBooksDataProvider.execute(parameters, 1, 3, orderBy);

        assertThat(page.getContent()).isNotEmpty();

        assertThat(page).isNotNull();

        final List<Book> books = page.getContent();

        assertThat(books).isNotNull().isNotEmpty();

        final Pageable pageable = page.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isOne();
        assertThat(pageable.getSize()).isEqualTo(3);
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isEqualTo(4);
        assertThat(pageable.getTotalPages()).isEqualTo(2);

        final Book book = books.get(0);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isNotNull().isEqualTo(1L);

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to paging books if found with sorted order")
    @Test
    void successfulToPagingBooksIfFoundWithSortedOrder() {

        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>(null, null);

        final SearchBooksParameters parameters = new SearchBooksParameters(null, null, prices, null, creationDates, updateDates);
        final String orderBy = "creationDate:asc";

        final Page<Book> page = searchBooksDataProvider.execute(parameters, 1, 3, orderBy);

        assertThat(page.getContent()).isNotEmpty();

        assertThat(page).isNotNull();

        final List<Book> books = page.getContent();

        assertThat(books).isNotNull().isNotEmpty();

        final Pageable pageable = page.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isOne();
        assertThat(pageable.getSize()).isEqualTo(3);
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isEqualTo(4);
        assertThat(pageable.getTotalPages()).isEqualTo(2);

        final Book book = books.get(0);

        assertThat(book).isNotNull();
        assertThat(book.getId()).isNotNull().isEqualTo(2L);

        assertThat(bookRepository.count()).isEqualTo(4);
    }
}
