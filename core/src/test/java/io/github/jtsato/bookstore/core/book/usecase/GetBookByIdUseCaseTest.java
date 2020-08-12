package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.GetBookByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato  
 */

class GetBookByIdUseCaseTest {

    @Mock
    private final GetBookByIdGateway getBookByIdGateway = Mockito.mock(GetBookByIdGateway.class);

    @InjectMocks
    private final GetBookByIdUseCase getBookByIdUseCase = new GetBookByIdUseCaseImpl(getBookByIdGateway);

    @DisplayName("Fail to get an book by id if parameters are not valid")
    @Test
    void failToGetBookByIdIfParametersAreNotValid() {

        when(getBookByIdGateway.getBookById(null)).thenReturn(null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookByIdUseCase.execute(null);
        });

        assertThat(exception).isInstanceOf(InvalidParameterException.class);
        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.book.id.null");
    }

    @DisplayName("Successful to get author by id if found")
    @Test
    void successfulToGetBookByIdIfFound() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(mockGetBookByIdGatewayOut());

        final Book book = getBookByIdUseCase.execute(1L);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(book.getAvailable()).isEqualTo(Boolean.TRUE);
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final Author author = book.getAuthor();

        assertThat(author).isNotNull();
        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Optional<Book> mockGetBookByIdGatewayOut() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L,
                                    author,
                                    "Effective Java (2nd Edition)",
                                    BigDecimal.valueOf(10.00),
                                    Boolean.TRUE,
                                    LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                    LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }

    @DisplayName("Fail to get book by id if not found")
    @Test
    void failToGetBookByIdIfNotFound() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookByIdUseCase.execute(1L);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }
}
