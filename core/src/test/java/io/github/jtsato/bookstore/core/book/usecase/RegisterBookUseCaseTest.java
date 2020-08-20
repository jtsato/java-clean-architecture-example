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
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.RegisterBookUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;


/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Register Book")
class RegisterBookUseCaseTest {

    @Mock
    private final RegisterBookGateway registerBookGateway = Mockito.mock(RegisterBookGateway.class);

    @Mock
    private final GetAuthorByIdGateway getAuthorByIdGateway = Mockito.mock(GetAuthorByIdGateway.class);

    @Mock
    private final GetBookByTitleGateway getBookByTitleGateway = Mockito.mock(GetBookByTitleGateway.class);

    @Mock
    private final GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);

    @InjectMocks
    private final RegisterBookUseCase registerBookUseCase = new RegisterBookUseCaseImpl(registerBookGateway,
                                                                                        getAuthorByIdGateway,
                                                                                        getBookByTitleGateway,
                                                                                        getLocalDateTime);

    @DisplayName("Fail to register a book if parameters are not valid")
    @Test
    void failToRegisterABookIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> new RegisterBookParameters(null, StringUtils.SPACE, null, null));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);

        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("title: validation.book.title.blank");
        assertThat(constraintViolationException.getMessage()).contains("authorId: validation.author.id.null");
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.null");
        assertThat(constraintViolationException.getMessage()).contains("available: validation.book.available.null");
    }

    @DisplayName("Fail to register a book if price is negative")
    @Test
    void failToRegisterABookIfPriceIsNegative() {

        final Exception exception = Assertions.assertThrows(Exception.class,
                                                            () -> new RegisterBookParameters(1L,
                                                                                             "Effective Java (2nd Edition)",
                                                                                             BigDecimal.valueOf(-1.00),
                                                                                             Boolean.TRUE));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);

        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.negative");
    }

    @DisplayName("Successful to register book if not registered")
    @Test
    void successfulToRegisterBookIfNotRegistered() {

        when(registerBookGateway.execute(mockRegisterBookGatewayIn())).thenReturn(mockRegisterBookGatewayOut());
        when(getAuthorByIdGateway.execute(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.execute("Effective Java (2nd Edition)")).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters(1L,
                                                                                         "Effective Java (2nd Edition)",
                                                                                         BigDecimal.valueOf(10.00),
                                                                                         Boolean.TRUE);
        final Book book = registerBookUseCase.execute(registerBookParameters);

        assertThat(book.getId()).isEqualTo(1L);
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.valueOf(10.00));
        assertThat(book.getAvailable()).isEqualTo(Boolean.TRUE);
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final Author author = book.getAuthor();

        assertThat(author).isNotNull();
        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Book mockRegisterBookGatewayIn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(null,
                        author,
                        "Effective Java (2nd Edition)",
                        BigDecimal.valueOf(10.00),
                        Boolean.TRUE,
                        LocalDateTime.parse("2020-03-12T22:04:59.123"),
                        LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private Book mockRegisterBookGatewayOut() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L,
                        author,
                        "Effective Java (2nd Edition)",
                        BigDecimal.valueOf(10.00),
                        Boolean.TRUE,
                        LocalDateTime.parse("2020-03-12T22:04:59.123"),
                        LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private Optional<Author> mockGetAuthorByIdGateway() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    @DisplayName("Fail to register book if already registered")
    @Test
    void failToRegisterBookIfAlreadyRegistered() {

        when(registerBookGateway.execute(mockRegisterBookGatewayIn())).thenReturn(mockRegisterBookGatewayOut());
        when(getAuthorByIdGateway.execute(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.execute("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayOut());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters(1L,
                                                                                         "Effective Java (2nd Edition)",
                                                                                         BigDecimal.valueOf(10.00),
                                                                                         Boolean.TRUE);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> registerBookUseCase.execute(registerBookParameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);

        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.book.title.already.exists");
    }

    private Optional<Book> mockGetBookByTitleGatewayOut() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L,
                                    author,
                                    "Effective Java (2nd Edition)",
                                    BigDecimal.valueOf(10.00),
                                    Boolean.TRUE,
                                    LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                    LocalDateTime.parse("2020-03-12T22:04:59.123")));
    }

    @DisplayName("Fail to register book if author not found")
    @Test
    void failToRegisterBookIfAuthorNotFound() {

        when(registerBookGateway.execute(mockRegisterBookGatewayIn())).thenReturn(mockRegisterBookGatewayOut());
        when(getAuthorByIdGateway.execute(1L)).thenReturn(Optional.empty());
        when(getBookByTitleGateway.execute("Joshua Bloch")).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters(1L,
                                                                                         "Effective Java (2nd Edition)",
                                                                                         BigDecimal.valueOf(10.00),
                                                                                         Boolean.TRUE);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> registerBookUseCase.execute(registerBookParameters));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
