package io.github.jtsato.bookstore.core.author.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.usecase.impl.GetAuthorByIdUseCaseImpl;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import io.github.jtsato.bookstore.core.exception.NotFoundException;

/**
 * @author Jorge Takeshi Sato
 */

class GetAuthorByIdUseCaseTest {

    @Mock
    private final GetAuthorByIdGateway getAuthorByIdGateway = Mockito.mock(GetAuthorByIdGateway.class);

    @InjectMocks
    private final GetAuthorByIdUseCase getAuthorByIdUseCase = new GetAuthorByIdUseCaseImpl(getAuthorByIdGateway);

    @DisplayName("Fail to get an author by id if parameters are not valid")
    @Test
    void failToGetAuthorByIdIfParametersAreNotValid() {

        when(getAuthorByIdGateway.execute(null)).thenReturn(null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> getAuthorByIdUseCase.getAuthorById(null));

        assertThat(exception).isInstanceOf(InvalidParameterException.class);

        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.author.id.null");
    }

    @DisplayName("Successful to get author by id if found")
    @Test
    void successfulToGetAuthorByIdIfFound() {

        when(getAuthorByIdGateway.execute(1L)).thenReturn(mockGetAuthorByIdGatewayOut());

        final Author author = getAuthorByIdUseCase.getAuthorById(1L);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Optional<Author> mockGetAuthorByIdGatewayOut() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    @DisplayName("Fail to get author by id if not found")
    @Test
    void failToGetAuthorByIdIfNotFound() {

        when(getAuthorByIdGateway.execute(1L)).thenReturn(Optional.empty());

        final Exception exception = Assertions.assertThrows(Exception.class, () -> getAuthorByIdUseCase.getAuthorById(1L));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
