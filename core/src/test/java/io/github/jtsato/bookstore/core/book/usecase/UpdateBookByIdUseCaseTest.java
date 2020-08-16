package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByTitleGateway;
import io.github.jtsato.bookstore.core.book.gateway.UpdateBookByIdGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.UpdateBookByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;

/**
 * @author Jorge Takeshi Sato
 */

class UpdateBookByIdUseCaseTest {

    @Mock
    private final UpdateBookByIdGateway updateBookByIdGateway = Mockito.mock(UpdateBookByIdGateway.class);

    @Mock
    private final GetAuthorByIdGateway getAuthorByIdGateway = Mockito.mock(GetAuthorByIdGateway.class);

    @Mock
    private final GetBookByTitleGateway getBookByTitleGateway = Mockito.mock(GetBookByTitleGateway.class);

    @Mock
    private final GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);

    @InjectMocks
    private final UpdateBookByIdUseCase updateBookByIdUseCase = new UpdateBookByIdUseCaseImpl(updateBookByIdGateway,
                                                                                              getAuthorByIdGateway,
                                                                                              getBookByTitleGateway,
                                                                                              getLocalDateTime);

    @DisplayName("Fail to update a book if parameters are not valid")
    @Test
    void failToUpdateABookIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> new UpdateBookByIdParameters(null, null, StringUtils.SPACE, null, null));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("id: validation.book.id.null");
        assertThat(constraintViolationException.getMessage()).contains("authorId: validation.author.id.null");
        assertThat(constraintViolationException.getMessage()).contains("title: validation.book.title.blank");
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.null");
        assertThat(constraintViolationException.getMessage()).contains("available: validation.book.available.null");
    }

    @DisplayName("Fail to update a book if price is negative")
    @Test
    void failToRegisterABookIfPriceIsNegative() {

        final Exception exception = Assertions.assertThrows(Exception.class,
                                                            () -> new UpdateBookByIdParameters(1L,
                                                                                               1L,
                                                                                               "Effective Java (2nd Edition)",
                                                                                               BigDecimal.valueOf(-1.00),
                                                                                               Boolean.TRUE));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);

        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.negative");
    }

    @DisplayName("Successful to update book by id if found")
    @Test
    void successfulToUpdateBookByIdIfFound() {

        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayIn())).thenReturn(mockUpdateBookByIdGatewayOut());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(Optional.empty());

        updateBookById();

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayIn())).thenReturn(mockUpdateBookByIdGatewayOut());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithSameId());

        updateBookById();
    }

    private Book mockUpdateBookByIdGatewayIn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L,
                        author,
                        "Effective Java (2nd Edition)",
                        BigDecimal.valueOf(10.00),
                        Boolean.TRUE,
                        null,
                        LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<Book> mockUpdateBookByIdGatewayOut() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L,
                                    author,
                                    "Effective Java (2nd Edition)",
                                    BigDecimal.valueOf(10.00),
                                    Boolean.TRUE,
                                    LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                    LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }

    private Optional<Author> mockGetAuthorByIdGateway() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    private void updateBookById() {

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L,
                                                                                 1L,
                                                                                 "Effective Java (2nd Edition)",
                                                                                 BigDecimal.valueOf(10.00),
                                                                                 Boolean.TRUE);
        final Optional<Author> optional = mockGetAuthorByIdGateway();

        assertThat(optional).isPresent();

        final Author author = optional.get();

        final Book book = updateBookByIdUseCase.execute(parameters);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(book.getAvailable()).isEqualTo(Boolean.TRUE);
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        assertThat(book.getAuthor()).isEqualToComparingFieldByField(author);
    }

    @DisplayName("Fail to update book by id if not found")
    @Test
    void failToUpdateBookByIdIfNotFound() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayIn())).thenReturn(Optional.empty());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(Optional.empty());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L,
                                                                                 1L,
                                                                                 "Effective Java (2nd Edition)",
                                                                                 BigDecimal.valueOf(10.00),
                                                                                 Boolean.TRUE);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateBookByIdUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }

    @DisplayName("Fail to update book by id if has a book with same title")
    @Test
    void failToUpdateBookByIdIfHasABookWithSameTitle() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayIn())).thenReturn(Optional.empty());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithOtherId());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L,
                                                                                 1L,
                                                                                 "Effective Java (2nd Edition)",
                                                                                 BigDecimal.valueOf(10.00),
                                                                                 Boolean.TRUE);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateBookByIdUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.book.title.already.exists");
    }

    private Optional<Book> mockGetBookByTitleGatewayWithOtherId() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(2L,
                                    author,
                                    "Effective Java (2nd Edition)",
                                    BigDecimal.valueOf(10.00),
                                    Boolean.TRUE,
                                    LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                    LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }

    private Optional<Book> mockGetBookByTitleGatewayWithSameId() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L,
                                    author,
                                    "Effective Java (2nd Edition)",
                                    BigDecimal.valueOf(10.00),
                                    Boolean.TRUE,
                                    LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                    LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }

    @DisplayName("Fail to update book by id if author no found")
    @Test
    void failToUpdateBookByIdIfAuthorNotFound() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayIn())).thenReturn(mockUpdateBookByIdGatewayOut());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(Optional.empty());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithSameId());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L,
                                                                                 1L,
                                                                                 "Effective Java (2nd Edition)",
                                                                                 BigDecimal.valueOf(10.00),
                                                                                 Boolean.TRUE);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateBookByIdUseCase.execute(parameters));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
