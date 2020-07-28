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
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookContentByBookIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookContentGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.SaveBookContentUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookContentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato  
 */

class SaveBookContentUseCaseTest {

    @Mock
    private SaveBookContentGateway saveBookContentGateway = Mockito.mock(SaveBookContentGateway.class);
    
    @Mock
    private GetBookByIdGateway getBookByIdGateway = Mockito.mock(GetBookByIdGateway.class);
    
    @Mock
    private GetBookContentByBookIdGateway getBookContentByBookIdGateway = Mockito.mock(GetBookContentByBookIdGateway.class);

    @Mock
    private GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);
    
    @InjectMocks
    private SaveBookContentUseCase getBookContentByBookIdUseCase = new SaveBookContentUseCaseImpl(saveBookContentGateway, getBookByIdGateway, getBookContentByBookIdGateway, getLocalDateTime);

    @DisplayName("Fail to register a book content if parameters are not valid")
    @Test
    void failToRegisterABookContentIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new SaveBookContentParameters(null, StringUtils.SPACE);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("bookId: validation.book.id.null");
        assertThat(constraintViolationException.getMessage()).contains("content: validation.book.content.blank");
    }
    
    @DisplayName("Successful to register book content if not registered")
    @Test
    void successfulToSaveBookContentIfNotRegistered() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(mockGetBookByIdGatewayReturn());
        when(getBookContentByBookIdGateway.getBookContentByBookId(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookContentGateway.saveBookContent(mockSaveBookContentGatewayParameters())).thenReturn(mockSaveBookContentGatewayReturn());

        final SaveBookContentParameters saveBookContentParameters = new SaveBookContentParameters(1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        final BookContent bookContent = getBookContentByBookIdUseCase.saveBookContent(saveBookContentParameters);

        assertThat(bookContent.getId()).isEqualTo(1L);
        assertThat(bookContent.getBookId()).isEqualTo(1L);
        assertThat(bookContent.getContent()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        assertThat(bookContent.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookContent.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<Book> mockGetBookByIdGatewayReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
		return Optional.of(new Book(1L, author, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE, LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }
    
    private BookContent mockSaveBookContentGatewayParameters() {
        return new BookContent(null, 1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private BookContent mockSaveBookContentGatewayReturn() {
        return new BookContent(1L, 1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    @DisplayName("Successful to update book content if already registered")
    @Test
    void successfulToUpdateBookContentIfAlreadyRegistered() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(mockGetBookByIdGatewayReturn());
        when(getBookContentByBookIdGateway.getBookContentByBookId(1L)).thenReturn(GetBookContentByBookIdGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookContentGateway.saveBookContent(mockSaveBookContentGatewayParameters())).thenReturn(mockSaveBookContentGatewayReturn());
        
        final SaveBookContentParameters saveBookContentParameters = new SaveBookContentParameters(1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        final BookContent bookContent = getBookContentByBookIdUseCase.saveBookContent(saveBookContentParameters);

        assertThat(bookContent.getId()).isEqualTo(1L);
        assertThat(bookContent.getBookId()).isEqualTo(1L);
        assertThat(bookContent.getContent()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        assertThat(bookContent.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookContent.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<BookContent> GetBookContentByBookIdGatewayReturn() {
        return Optional.of(new BookContent(null, 1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.", LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123")));
	}

	@DisplayName("Fail to save book content if book not found")
    @Test
    void failToSaveBookContentIfBookNotFound() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(Optional.empty());
        when(getBookContentByBookIdGateway.getBookContentByBookId(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookContentGateway.saveBookContent(mockSaveBookContentGatewayParameters())).thenReturn(mockSaveBookContentGatewayReturn());

        final SaveBookContentParameters saveBookContentParameters = new SaveBookContentParameters(1L, "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookContentByBookIdUseCase.saveBookContent(saveBookContentParameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }
}
