package io.github.jtsato.bookstore.core.author.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.core.author.action.UpdateAuthorByIdAction;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Update Author By Id")
class UpdateAuthorByIdUseCaseTest {

    @Mock
    private final UpdateAuthorByIdGateway updateAuthorByIdGateway = Mockito.mock(UpdateAuthorByIdGateway.class);

    @Mock
    private final GetAuthorByNameGateway getAuthorByNameGateway = Mockito.mock(GetAuthorByNameGateway.class);

    @InjectMocks
    private final UpdateAuthorByIdUseCase updateAuthorByIdUseCase = new UpdateAuthorByIdAction(updateAuthorByIdGateway, getAuthorByNameGateway);

    @DisplayName("Fail to update an author if parameters are not valid")
    @Test
    void failToUpdateAnAuthorIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> new UpdateAuthorByIdParameters(null, StringUtils.SPACE, null, "1979-02-29"));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("id: validation.author.id.null");
        assertThat(constraintViolationException.getMessage()).contains("name: validation.author.name.blank");
        assertThat(constraintViolationException.getMessage()).contains("gender: validation.author.gender.blank");
        assertThat(constraintViolationException.getMessage()).contains("birthdate: validation.author.birthdate.invalid");
    }

    @DisplayName("Successful to update author by id if found")
    @Test
    void successfulToUpdateAuthorByIdIfFound() {

        when(updateAuthorByIdGateway.execute(mockUpdateAuthorByIdGatewayIn())).thenReturn(mockUpdateAuthorByIdGatewayOut());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(Optional.empty());

        updateAuthorById();

        when(updateAuthorByIdGateway.execute(mockUpdateAuthorByIdGatewayIn())).thenReturn(mockUpdateAuthorByIdGatewayOut());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(mockGetAuthorByNameGatewayWithSameId());

        updateAuthorById();
    }

    private Author mockUpdateAuthorByIdGatewayIn() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }

    private Optional<Author> mockUpdateAuthorByIdGatewayOut() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    private void updateAuthorById() {

        final UpdateAuthorByIdParameters parameters = new UpdateAuthorByIdParameters(1L, "Joshua Bloch", "MALE", "1961-08-28");
        final Author author = updateAuthorByIdUseCase.updateAuthorById(parameters);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    @DisplayName("Fail to update author by id if found and name is duplicated")
    @Test
    void failToUpdateAuthorByIdIfFoundAndNameIsDuplicated() {

        when(updateAuthorByIdGateway.execute(mockUpdateAuthorByIdGatewayIn())).thenReturn(Optional.empty());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(mockGetAuthorByNameGatewayWithOtherId());

        final UpdateAuthorByIdParameters parameters = new UpdateAuthorByIdParameters(1L, "Joshua Bloch", "MALE", "1961-08-28");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateAuthorByIdUseCase.updateAuthorById(parameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.author.name.already.exists");
    }

    private Optional<Author> mockGetAuthorByNameGatewayWithSameId() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    private Optional<Author> mockGetAuthorByNameGatewayWithOtherId() {
        return Optional.of(new Author(2L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }

    @DisplayName("Fail to update author by id if not found")
    @Test
    void failToUpdateAuthorByIdIfNotFound() {

        when(updateAuthorByIdGateway.execute(mockUpdateAuthorByIdGatewayIn())).thenReturn(Optional.empty());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(Optional.empty());

        final UpdateAuthorByIdParameters parameters = new UpdateAuthorByIdParameters(1L, "Joshua Bloch", "MALE", "1961-08-28");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateAuthorByIdUseCase.updateAuthorById(parameters));

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }

    @DisplayName("Fail to update author by id if has an author with same name")
    @Test
    void failToUpdateAuthorByIdIfHasAnAuthorWithSameName() {

        when(updateAuthorByIdGateway.execute(mockUpdateAuthorByIdGatewayIn())).thenReturn(Optional.empty());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(mockGetAuthorByNameWithOtherIdGateway());

        final UpdateAuthorByIdParameters parameters = new UpdateAuthorByIdParameters(1L, "Joshua Bloch", "MALE", "1961-08-28");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> updateAuthorByIdUseCase.updateAuthorById(parameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.author.name.already.exists");
    }

    private Optional<Author> mockGetAuthorByNameWithOtherIdGateway() {
        return Optional.of(new Author(2L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }
}
