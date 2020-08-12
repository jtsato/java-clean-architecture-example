package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.FindBooksByIdsGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.FindBooksByIdsUseCaseImpl;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;

/**
 * @book Jorge Takeshi Sato  
 */

class FindBooksByIdsUseCaseTest {

    @Mock
    private final FindBooksByIdsGateway findBooksByIdsGateway = Mockito.mock(FindBooksByIdsGateway.class);

    @InjectMocks
    private final FindBooksByIdsUseCase findBooksByIdsUseCase = new FindBooksByIdsUseCaseImpl(findBooksByIdsGateway);

    @DisplayName("Fail to find books by IDs if parameters are not valid")
    @Test
    void failToFindBooksByIdsIfParametersAreNotValid() {

        when(findBooksByIdsGateway.findBooksByIds(null)).thenReturn(null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            findBooksByIdsUseCase.execute(null);
        });

        assertThat(exception).isInstanceOf(InvalidParameterException.class);

        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.books.ids.null");
    }

    @DisplayName("Successful to find books by IDs, when at least one is found")
    @Test
    void successfulToFindBooksByIdsIfFound() {

        final List<Long> ids = Arrays.asList(new Long[] {1L, 2L});

        when(findBooksByIdsGateway.findBooksByIds(ids)).thenReturn(mockFindBooksByIdsGatewayOut());

        final Page<Book> pageOfBooks = findBooksByIdsUseCase.execute(ids);

        assertThat(pageOfBooks).isNotNull();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        final List<Book> books = pageOfBooks.getContent();

        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books.size()).isOne();

        final Book book = books.get(0);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(book.getAvailable()).isEqualTo(Boolean.TRUE);
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final Author author = book.getAuthor();

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Page<Book> mockFindBooksByIdsGatewayOut() {

        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));

        final List<Book> content = new ArrayList<>(1);
        content.add(new Book(1L,
                             author,
                             "Effective Java (2nd Edition)",
                             BigDecimal.valueOf(10.00),
                             Boolean.TRUE,
                             LocalDateTime.parse("2020-03-12T22:04:59.123"),
                             LocalDateTime.parse("2020-04-12T22:04:59.123")));

        return new PageImpl<Book>(content, new Pageable(0, 1, 1, 1L, 1));
    }

    @DisplayName("Fail to find books by IDs if not found")
    @Test
    void failToFindBooksByIdsIfNotFound() {

        final List<Long> ids = Arrays.asList(new Long[] {1L, 2L});

        final Page<Book> pageOfBooks = new PageImpl<Book>(Collections.emptyList(), new Pageable(0, 1, 0, 0L, 0));

        when(findBooksByIdsGateway.findBooksByIds(ids)).thenReturn(pageOfBooks);

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();
    }

    @DisplayName("Fail to find books by IDs if the limit is exceeded")
    @Test
    void failedToFindBooksByIdsIfTheLimitIsExceeded() {

        final List<Long> ids = new ArrayList<Long>(1001);

        for (int index = 1; index <= 1001; index++) {
            ids.add((long) index);
        }

        when(findBooksByIdsGateway.findBooksByIds(ids)).thenReturn(mockFindBooksByIdsGatewayOut());

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            findBooksByIdsUseCase.execute(ids);
        });

        assertThat(exception).isInstanceOf(InvalidParameterException.class);

        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.get.by.ids.limit");
    }

    @DisplayName("Successful to find books by IDs if the limit is not exceeded")
    @Test
    void successfulToFindBooksByIdsIfTheLimitIsNotExceeded() {

        final List<Long> ids = new ArrayList<Long>(1000);

        for (int index = 1; index <= 1000; index++) {
            ids.add((long) index);
        }

        when(findBooksByIdsGateway.findBooksByIds(ids)).thenReturn(mockFindBooksByIdsGatewayOut());

        final Page<Book> pageOfBooks = findBooksByIdsUseCase.execute(ids);

        assertThat(pageOfBooks).isNotNull();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        final List<Book> books = pageOfBooks.getContent();

        assertThat(books).isNotNull().isNotEmpty();
        assertThat(books.size()).isOne();

        final Book book = books.get(0);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(book.getAvailable()).isEqualTo(Boolean.TRUE);
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final Author author = book.getAuthor();

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }
}
