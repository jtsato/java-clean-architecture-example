package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;

/**
 * @author Jorge Takeshi Sato  
 */

class UpdateBookByIdUseCaseTest {

    @Mock
    private UpdateBookByIdGateway updateBookByIdGateway = Mockito.mock(UpdateBookByIdGateway.class);

    @Mock
    private GetAuthorByIdGateway getAuthorByIdGateway = Mockito.mock(GetAuthorByIdGateway.class);

    @Mock
    private GetBookByTitleGateway getBookByTitleGateway = Mockito.mock(GetBookByTitleGateway.class);

    @InjectMocks
    private UpdateBookByIdUseCase updateBookByIdUseCase = new UpdateBookByIdUseCaseImpl(updateBookByIdGateway, getAuthorByIdGateway, getBookByTitleGateway);

    @DisplayName("Fail to update a book if parameters are not valid")
    @Test
    void failToUpdateABookIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new UpdateBookByIdParameters(null, StringUtils.SPACE, null, null);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("id: validation.book.id.null");
        assertThat(constraintViolationException.getMessage()).contains("title: validation.book.title.blank");
        assertThat(constraintViolationException.getMessage()).contains("authorId: validation.author.id.null");
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.null");        
    }
    
    @DisplayName("Fail to update a book if price is negative")
    @Test
    void failToRegisterABookIfPriceIsNegative() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new UpdateBookByIdParameters(1L, "Effective Java (2nd Edition)", 1L, BigDecimal.valueOf(-1.00));
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("price: validation.book.price.negative");
    } 
    
    @DisplayName("Successful to update book by id if found")
    @Test
    void successfulToUpdateBookByIdIfFound() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayParameters())).thenReturn(mockUpdateBookByIdGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(Optional.empty());

        updateBookById();

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayParameters())).thenReturn(mockUpdateBookByIdGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithSameId());

        updateBookById();
    }

    private Book mockUpdateBookByIdGatewayParameters() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L, "Effective Java (2nd Edition)", null, BigDecimal.valueOf(10.00), author);
    }

    private Optional<Book> mockUpdateBookByIdGatewayReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L, "Effective Java (2nd Edition)", null, BigDecimal.valueOf(10.00), author));
    }

    private Optional<Author> mockGetAuthorByIdGateway() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    private void updateBookById() {

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L, "Effective Java (2nd Edition)", 1L, BigDecimal.valueOf(10.00));
        final Author author = mockGetAuthorByIdGateway().get();

        final Book book = updateBookByIdUseCase.updateBookById(parameters);

        assertThat(book.getId()).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(book.getCreationDate()).isNull();

        assertThat(book.getAuthor()).isEqualToComparingFieldByField(author);
    }

    @DisplayName("Fail to update book by id if not found")
    @Test
    void failToUpdateBookByIdIfNotFound() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayParameters())).thenReturn(Optional.empty());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(Optional.empty());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L, "Effective Java (2nd Edition)", 1L, BigDecimal.valueOf(10.00));

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            updateBookByIdUseCase.updateBookById(parameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }

    @DisplayName("Fail to update book by id if has a book with same title")
    @Test
    void failToUpdateBookByIdIfHasABookWithSameTitle() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayParameters())).thenReturn(Optional.empty());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(mockGetAuthorByIdGateway());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithOtherId());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L, "Effective Java (2nd Edition)", 1L, BigDecimal.valueOf(10.00));

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            updateBookByIdUseCase.updateBookById(parameters);
        });

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.book.title.already.exists");
    }

    private Optional<Book> mockGetBookByTitleGatewayWithOtherId() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(2L, "Effective Java (2nd Edition)", null, BigDecimal.valueOf(10.00), author));
    }
    
    private Optional<Book> mockGetBookByTitleGatewayWithSameId() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return Optional.of(new Book(1L, "Effective Java (2nd Edition)", null, BigDecimal.valueOf(10.00), author));
    }

    @DisplayName("Fail to update book by id if author no found")
    @Test
    void failToUpdateBookByIdIfAuthorNotFound() {

        when(updateBookByIdGateway.updateBookById(mockUpdateBookByIdGatewayParameters())).thenReturn(mockUpdateBookByIdGatewayReturn());
        when(getAuthorByIdGateway.getAuthorById(1L)).thenReturn(Optional.empty());
        when(getBookByTitleGateway.getBookByTitle("Effective Java (2nd Edition)")).thenReturn(mockGetBookByTitleGatewayWithSameId());

        final UpdateBookByIdParameters parameters = new UpdateBookByIdParameters(1L, "Effective Java (2nd Edition)", 1L, BigDecimal.valueOf(10.00));

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            updateBookByIdUseCase.updateBookById(parameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
