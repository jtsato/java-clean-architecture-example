package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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

class RegisterBookUseCaseTest {

    @Mock
    private RegisterBookGateway registerBookGateway = Mockito.mock(RegisterBookGateway.class);

    @Mock
    private GetAuthorByIdGateway getAuthorByIdGateway = Mockito.mock(GetAuthorByIdGateway.class);

    @Mock
    private GetBookByTitleGateway getBookByTitleGateway = Mockito.mock(GetBookByTitleGateway.class);

    @Mock
    private GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);

    @InjectMocks
    private RegisterBookUseCase getBookByIdUseCase = new RegisterBookUseCaseImpl(registerBookGateway, getAuthorByIdGateway, getBookByTitleGateway, getLocalDateTime);

    @DisplayName("Fail to register a book if parameters are not valid")
    @Test
    void failToRegisterABookIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new RegisterBookParameters(StringUtils.SPACE, null);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("title: validation.book.title.blank");
        assertThat(constraintViolationException.getMessage()).contains("authorId: validation.author.id.null");
    }
    
    @DisplayName("Successful to register author if not registered")
    @Test
    void successfulToRegisterBookIfNotRegistered() {

        when(registerBookGateway.registerBook(mockRegisterBookGatewayParameters())).thenReturn(mockRegisterBookGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Joshua Bloch")).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters("Effective Java (2nd Edition)", 1L);
        final Book book = getBookByIdUseCase.registerBook(registerBookParameters);

        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(book.getAuthor().getId()).isEqualTo(1L);
    }

    private Book mockRegisterBookGatewayParameters() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(null, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author);
    }

    private Book mockRegisterBookGatewayReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author);
    }

    private Optional<Author> mockGetAuthorByIdGateway() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    @DisplayName("Fail to register book if already registered")
    @Test
    void failToRegisterBookIfAlreadyRegistered() {

        when(registerBookGateway.registerBook(mockRegisterBookGatewayParameters())).thenReturn(mockRegisterBookGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters("Effective Java (2nd Edition)", 1L);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookByIdUseCase.registerBook(registerBookParameters);
        });

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.book.title.already.exists");
    }

    private Optional<Book> mockGetBookByTitleGatewayReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author));
    }    
    
    @DisplayName("Fail to register book if author not found")
    @Test
    void failToRegisterBookIfAuthorNotFound() {

        when(registerBookGateway.registerBook(mockRegisterBookGatewayParameters())).thenReturn(mockRegisterBookGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(Optional.empty());
        when(getBookByTitleGateway.getBookByTitle("Joshua Bloch")).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookParameters registerBookParameters = new RegisterBookParameters("Effective Java (2nd Edition)", 1L);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookByIdUseCase.registerBook(registerBookParameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
