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
import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.GetBookDocumentByBookIdGateway;
import io.github.jtsato.bookstore.core.book.gateway.SaveBookDocumentGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.SaveBookDocumentUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato  
 */

class SaveBookDocumentUseCaseTest {

    @Mock
    private SaveBookDocumentGateway saveBookDocumentGateway = Mockito.mock(SaveBookDocumentGateway.class);
    
    @Mock
    private GetBookByIdGateway getBookByIdGateway = Mockito.mock(GetBookByIdGateway.class);
    
    @Mock
    private GetBookDocumentByBookIdGateway getBookDocumentByBookIdGateway = Mockito.mock(GetBookDocumentByBookIdGateway.class);

    @Mock
    private GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);
    
    @InjectMocks
    private SaveBookDocumentUseCase getBookDocumentByBookIdUseCase = new SaveBookDocumentUseCaseImpl(saveBookDocumentGateway, getBookByIdGateway, getBookDocumentByBookIdGateway, getLocalDateTime);

    @DisplayName("Fail to register a book document if parameters are not valid")
    @Test
    void failToRegisterABookDocumentIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new SaveBookDocumentParameters(null, null, null, StringUtils.SPACE, 0L, null);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("bookId: validation.book.id.null");
        assertThat(constraintViolationException.getMessage()).contains("content: validation.book.content.blank");
    }
    
    @DisplayName("Successful to register book document if not registered")
    @Test
    void successfulToSaveBookDocumentIfNotRegistered() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(mockGetBookByIdGatewayReturn());
        when(getBookDocumentByBookIdGateway.getBookDocumentByBookId(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookDocumentGateway.saveBookDocument(mockSaveBookDocumentGatewayParameters())).thenReturn(mockSaveBookDocumentGatewayReturn());

		final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L, "text/plain",
				"txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
		
        final BookDocument bookDocument = getBookDocumentByBookIdUseCase.saveBookDocument(saveBookDocumentParameters);

        assertThat(bookDocument.getId()).isEqualTo(1L);
        assertThat(bookDocument.getBookId()).isEqualTo(1L);
        assertThat(bookDocument.getContentType()).isEqualTo("text/plain");
        assertThat(bookDocument.getExtension()).isEqualTo("txt");
        assertThat(bookDocument.getName()).isEqualTo("Document");
        assertThat(bookDocument.getSize()).isEqualTo(123L);
        assertThat(bookDocument.getContent()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        assertThat(bookDocument.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookDocument.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<Book> mockGetBookByIdGatewayReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
		return Optional.of(new Book(1L, author, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE, LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-04-12T22:04:59.123")));
    }
    
    private BookDocument mockSaveBookDocumentGatewayParameters() {
		return new BookDocument(null, 1L, "text/plain", "txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
				LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private BookDocument mockSaveBookDocumentGatewayReturn() {
		return new BookDocument(1L, 1L, "text/plain", "txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
				LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    @DisplayName("Successful to update book document if already registered")
    @Test
    void successfulToUpdateBookDocumentIfAlreadyRegistered() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(mockGetBookByIdGatewayReturn());
        when(getBookDocumentByBookIdGateway.getBookDocumentByBookId(1L)).thenReturn(GetBookDocumentByBookIdGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookDocumentGateway.saveBookDocument(mockSaveBookDocumentGatewayParameters())).thenReturn(mockSaveBookDocumentGatewayReturn());
        
		final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L, "text/plain",
				"txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
		
        final BookDocument bookDocument = getBookDocumentByBookIdUseCase.saveBookDocument(saveBookDocumentParameters);

        assertThat(bookDocument.getId()).isEqualTo(1L);
        assertThat(bookDocument.getBookId()).isEqualTo(1L);
        assertThat(bookDocument.getContentType()).isEqualTo("text/plain");
        assertThat(bookDocument.getExtension()).isEqualTo("txt");
        assertThat(bookDocument.getName()).isEqualTo("Document");
        assertThat(bookDocument.getSize()).isEqualTo(123L);
        assertThat(bookDocument.getContent()).isEqualTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        assertThat(bookDocument.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookDocument.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<BookDocument> GetBookDocumentByBookIdGatewayReturn() {
		return Optional.of(new BookDocument(null, 1L, "text/plain", "txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
				LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123")));
	}

	@DisplayName("Fail to save book document if book not found")
    @Test
    void failToSaveBookDocumentIfBookNotFound() {

        when(getBookByIdGateway.getBookById(1L)).thenReturn(Optional.empty());
        when(getBookDocumentByBookIdGateway.getBookDocumentByBookId(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookDocumentGateway.saveBookDocument(mockSaveBookDocumentGatewayParameters())).thenReturn(mockSaveBookDocumentGatewayReturn());

		final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L, "text/plain",
				"txt", "Document", 123L,
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookDocumentByBookIdUseCase.saveBookDocument(saveBookDocumentParameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }
}
