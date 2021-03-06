package io.github.jtsato.bookstore.core.author.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.action.RemoveAuthorByIdAction;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.SearchBooksByAuthorIdGateway;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Remove Author By Id")
class RemoveAuthorByIdUseCaseTest {

    @Mock
    private final RemoveAuthorByIdGateway removeAuthorByIdGateway = Mockito.mock(RemoveAuthorByIdGateway.class);

    @Mock
    private final SearchBooksByAuthorIdGateway searchBooksByAuthorIdGateway = Mockito.mock(SearchBooksByAuthorIdGateway.class);

    @InjectMocks
    private final RemoveAuthorByIdUseCase removeAuthorByIdUseCase = new RemoveAuthorByIdAction(removeAuthorByIdGateway, searchBooksByAuthorIdGateway);

    @DisplayName("Fail to remove an author by id if parameters are not valid")
    @Test
    void failToRemoveAuthorByIdIfParametersAreNotValid() {

        when(removeAuthorByIdGateway.execute(null)).thenReturn(null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> removeAuthorByIdUseCase.execute(null));

        assertThat(exception).isInstanceOf(InvalidParameterException.class);
        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.author.id.null");
    }

    @DisplayName("Successful to remove author by id if found")
    @Test
    void successfulToRemoveAuthorByIdIfFound() {

        when(removeAuthorByIdGateway.execute(1L)).thenReturn(mockRemoveAuthorByIdGatewayOut());
        final Page<Book> emptyBookPage = new PageImpl<>(Collections.emptyList(), new Pageable(0, 0, 0, 0L, 0));
        when(searchBooksByAuthorIdGateway.execute(1L, 0, 1, "id:asc")).thenReturn(emptyBookPage);

        final Author author = removeAuthorByIdUseCase.execute(1L);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Optional<Author> mockRemoveAuthorByIdGatewayOut() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    @DisplayName("Fail to remove author by id if not found")
    @Test
    void failToRemoveAuthorByIdIfNotFound() {

        when(removeAuthorByIdGateway.execute(1L)).thenReturn(Optional.empty());
        when(searchBooksByAuthorIdGateway.execute(1L, 0, 1, "id:asc")).thenReturn(mockEmptyBooksPage());

        final Exception exception = Assertions.assertThrows(Exception.class, () -> removeAuthorByIdUseCase.execute(1L));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }

    private Page<Book> mockEmptyBooksPage() {
        return new PageImpl<>(Collections.emptyList(), new Pageable(0, 1, 0, 0L, 0));
    }

    @DisplayName("Fail to remove author by id if has books associated")
    @Test
    void failToRemoveAuthorByIdIfHasBooksAssociated() {

        when(removeAuthorByIdGateway.execute(1L)).thenReturn(mockRemoveAuthorByIdGatewayOut());
        final Page<Book> bookPage = mockPageWithOneBook();
        when(searchBooksByAuthorIdGateway.execute(1L, 0, 1, "id:asc")).thenReturn(bookPage);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> removeAuthorByIdUseCase.execute(1L));

        assertThat(exception.getMessage()).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("validation.author.with.books.removal");
    }

    private Page<Book> mockPageWithOneBook() {

        final List<Book> content = new ArrayList<>(1);

        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));

        content.add(new Book(1L,
                             author,
                             "Effective Java (2nd Edition)",
                             BigDecimal.valueOf(10.00),
                             Boolean.TRUE,
                             LocalDateTime.parse("2020-03-12T22:04:59.123"),
                             LocalDateTime.parse("2020-03-12T22:04:59.123")));

        return new PageImpl<>(content, new Pageable(0, 1, 1, 1L, 1));
    }
}
