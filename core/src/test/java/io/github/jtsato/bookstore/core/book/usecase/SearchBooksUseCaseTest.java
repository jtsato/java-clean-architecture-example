package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.SearchBooksUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

class SearchBooksUseCaseTest {

    @Mock
    private final SearchBooksGateway searchBooksGateway = Mockito.mock(SearchBooksGateway.class);

    @InjectMocks
    private final SearchBooksUseCase searchBooksUseCase = new SearchBooksUseCaseImpl(searchBooksGateway);

    @DisplayName("Fail to search books with inconsistent parameters")
    @Test
    void failToSearchBooksWithInconsistentParameters() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
            final ImmutablePair<String, String> creationDates = new ImmutablePair<>("2019-02-29T00:00:00", "2019-02-29T23:59:59");
            final ImmutablePair<String, String> updateDates = new ImmutablePair<>("2019-02-29T00:00:00", "2019-02-29T23:59:59");
            new SearchBooksParameters(null, null, prices, null, creationDates, updateDates);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("startCreationDate: validation.book.start.creation.date.invalid");
        assertThat(constraintViolationException.getMessage()).contains("endCreationDate: validation.book.end.creation.date.invalid");
    }

    @DisplayName("Successful to search books if found")
    @Test
    void successfulToSearchBooksIfFound() {

        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>("2020-02-29T00:00:00", "2020-02-29T23:59:59");
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>("2020-02-29T00:00:00", "2020-02-29T23:59:59");
        final SearchBooksParameters parameters = new SearchBooksParameters(null, null, prices, null, creationDates, updateDates);
        final Page<Book> pageWithOneBook = mockPageWithOneBook();

        when(searchBooksGateway.execute(parameters, 0, 1, "id:asc")).thenReturn(pageWithOneBook);

        final Page<Book> pageOfBooks = searchBooksUseCase.execute(parameters, 0, 1, "id:asc");

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        assertThat(pageOfBooks).isNotNull();

        final List<Book> books = pageOfBooks.getContent();

        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books.size()).isOne();

        final Book book = books.get(0);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private Page<Book> mockPageWithOneBook() {

        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));

        final List<Book> content = new ArrayList<>(1);
        content.add(new Book(1L,
                             author,
                             "Effective Java (2nd Edition)",
                             BigDecimal.valueOf(10.00),
                             Boolean.TRUE,
                             LocalDateTime.parse("2020-03-12T22:04:59.123"),
                             LocalDateTime.parse("2020-03-12T22:04:59.123")));

        return new PageImpl<>(content, new Pageable(0, 1, 1, 1L, 1));
    }

    @DisplayName("Fail to search book by id if not found")
    @Test
    void successfulToSearchBooksIfNotFound() {

        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>(null, null);
        final SearchBooksParameters parameters = new SearchBooksParameters(null, null, prices, null, creationDates, updateDates);

        final Page<Book> emptyBooksPage = mockEmptyBooksPage();
        when(searchBooksGateway.execute(parameters, 0, 1, "id:asc")).thenReturn(emptyBooksPage);

        final Page<Book> pageOfBooks = searchBooksUseCase.execute(parameters, 0, 1, "id:asc");

        assertThat(pageOfBooks).isNotNull();

        final List<Book> books = pageOfBooks.getContent();

        assertThat(books).isNotNull().isEmpty();

        assertThat(pageOfBooks.getPageable()).isNotNull();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();
    }

    private Page<Book> mockEmptyBooksPage() {
        return new PageImpl<>(Collections.emptyList(), new Pageable(0, 1, 0, 0L, 0));
    }
}
