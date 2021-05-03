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
import io.github.jtsato.bookstore.core.book.action.SaveBookDocumentAction;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Save Book Document")
class SaveBookDocumentUseCaseTest {

    @Mock
    private final SaveBookDocumentGateway saveBookDocumentGateway = Mockito.mock(SaveBookDocumentGateway.class);

    @Mock
    private final GetBookByIdGateway getBookByIdGateway = Mockito.mock(GetBookByIdGateway.class);

    @Mock
    private final GetBookDocumentByBookIdGateway getBookDocumentByBookIdGateway = Mockito.mock(GetBookDocumentByBookIdGateway.class);

    @Mock
    private final GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);

    @InjectMocks
    private final SaveBookDocumentUseCase getBookDocumentByBookIdUseCase = new SaveBookDocumentAction(saveBookDocumentGateway,
                                                                                                           getBookByIdGateway,
                                                                                                           getBookDocumentByBookIdGateway,
                                                                                                           getLocalDateTime);

    @DisplayName("Fail to register a book document if parameters are not valid")
    @Test
    void failToRegisterABookDocumentIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class,
                                                            () -> new SaveBookDocumentParameters(null, null, null, StringUtils.SPACE, 0L, null));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);

        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("bookId: validation.book.id.null");
        assertThat(constraintViolationException.getMessage()).contains("content: validation.book.content.blank");
    }

    @DisplayName("Successful to register book document if not registered")
    @Test
    void successfulToSaveBookDocumentIfNotRegistered() {

        when(getBookByIdGateway.execute(1L)).thenReturn(mockGetBookByIdGatewayOut());
        when(getBookDocumentByBookIdGateway.execute(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-04-12T22:04:59.123"));
        when(saveBookDocumentGateway.execute(mockSaveBookDocumentGatewayIn1())).thenReturn(mockSaveBookDocumentGatewayOut());

        final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L,
                                                                                                     "text/plain",
                                                                                                     "txt",
                                                                                                     "Document",
                                                                                                     123L,
                                                                                                     "Lorem ipsum dolor sit amet.");

        final BookDocument bookDocument = getBookDocumentByBookIdUseCase.execute(saveBookDocumentParameters);

        assertThat(bookDocument.getId()).isEqualTo(1L);
        assertThat(bookDocument.getBookId()).isEqualTo(1L);
        assertThat(bookDocument.getContentType()).isEqualTo("text/plain");
        assertThat(bookDocument.getExtension()).isEqualTo("txt");
        assertThat(bookDocument.getName()).isEqualTo("Document");
        assertThat(bookDocument.getSize()).isEqualTo(123L);
        assertThat(bookDocument.getContent()).isEqualTo("Lorem ipsum dolor sit amet.");
        assertThat(bookDocument.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookDocument.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<Book> mockGetBookByIdGatewayOut() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        final Book book = new Book(1L,
                                   author,
                                   "Effective Java (2nd Edition)",
                                   BigDecimal.valueOf(10.00),
                                   Boolean.TRUE,
                                   LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                   LocalDateTime.parse("2020-04-12T22:04:59.123"));
        return Optional.of(book);
    }

    private BookDocument mockSaveBookDocumentGatewayIn1() {
        return new BookDocument(null,
                                1L,
                                "text/plain",
                                "txt",
                                "Document",
                                123L,
                                "Lorem ipsum dolor sit amet.",
                                LocalDateTime.parse("2020-04-12T22:04:59.123"),
                                LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private BookDocument mockSaveBookDocumentGatewayOut() {
        return new BookDocument(1L,
                                1L,
                                "text/plain",
                                "txt",
                                "Document",
                                123L,
                                "Lorem ipsum dolor sit amet.",
                                LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    @DisplayName("Successful to update book document if already registered")
    @Test
    void successfulToUpdateBookDocumentIfAlreadyRegistered() {

        when(getBookByIdGateway.execute(1L)).thenReturn(mockGetBookByIdGatewayOut());
        when(getBookDocumentByBookIdGateway.execute(1L)).thenReturn(getBookDocumentByBookIdGatewayOut());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-04-12T22:04:59.123"));
        when(saveBookDocumentGateway.execute(mockSaveBookDocumentGatewayIn2())).thenReturn(mockSaveBookDocumentGatewayOut());

        final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L,
                                                                                                     "text/plain",
                                                                                                     "txt",
                                                                                                     "Document",
                                                                                                     123L,
                                                                                                     "Lorem ipsum dolor sit amet.");

        final BookDocument bookDocument = getBookDocumentByBookIdUseCase.execute(saveBookDocumentParameters);

        assertThat(bookDocument.getId()).isEqualTo(1L);
        assertThat(bookDocument.getBookId()).isEqualTo(1L);
        assertThat(bookDocument.getContentType()).isEqualTo("text/plain");
        assertThat(bookDocument.getExtension()).isEqualTo("txt");
        assertThat(bookDocument.getName()).isEqualTo("Document");
        assertThat(bookDocument.getSize()).isEqualTo(123L);
        assertThat(bookDocument.getContent()).isEqualTo("Lorem ipsum dolor sit amet.");
        assertThat(bookDocument.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(bookDocument.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private Optional<BookDocument> getBookDocumentByBookIdGatewayOut() {
        return Optional.of(new BookDocument(1L,
                                            1L,
                                            "text/plain",
                                            "txt",
                                            "Document",
                                            123L,
                                            "Core competency market focus standpoint enterprise brand",
                                            LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                            LocalDateTime.parse("2020-03-12T22:04:59.123")));
    }

    private BookDocument mockSaveBookDocumentGatewayIn2() {
        return new BookDocument(1L,
                                1L,
                                "text/plain",
                                "txt",
                                "Document",
                                123L,
                                "Lorem ipsum dolor sit amet.",
                                LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    @DisplayName("Fail to save book document if book not found")
    @Test
    void failToSaveBookDocumentIfBookNotFound() {

        when(getBookByIdGateway.execute(1L)).thenReturn(Optional.empty());
        when(getBookDocumentByBookIdGateway.execute(1L)).thenReturn(Optional.empty());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        when(saveBookDocumentGateway.execute(mockSaveBookDocumentGatewayIn1())).thenReturn(mockSaveBookDocumentGatewayOut());

        final SaveBookDocumentParameters saveBookDocumentParameters = new SaveBookDocumentParameters(1L,
                                                                                                     "text/plain",
                                                                                                     "txt",
                                                                                                     "Document",
                                                                                                     123L,
                                                                                                     "Lorem ipsum dolor sit amet.");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> getBookDocumentByBookIdUseCase.execute(saveBookDocumentParameters));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.book.id.notfound");
    }
}
