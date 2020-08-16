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
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.core.author.usecase.impl.RegisterAuthorUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;


/**
 * @author Jorge Takeshi Sato
 */

class RegisterAuthorUseCaseTest {

    @Mock
    private final RegisterAuthorGateway registerAuthorGateway = Mockito.mock(RegisterAuthorGateway.class);

    @Mock
    private final GetAuthorByNameGateway getAuthorByNameGateway = Mockito.mock(GetAuthorByNameGateway.class);

    @InjectMocks
    private final RegisterAuthorUseCase getAuthorByIdUseCase = new RegisterAuthorUseCaseImpl(registerAuthorGateway, getAuthorByNameGateway);

    @DisplayName("Fail to register an author if parameters are not valid")
    @Test
    void failToRegisterAnAuthorIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> new RegisterAuthorParameters(StringUtils.SPACE, null, null));

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("name: validation.author.name.blank");
        assertThat(constraintViolationException.getMessage()).contains("gender: validation.author.gender.blank");
        assertThat(constraintViolationException.getMessage()).contains("birthdate: validation.author.birthdate.blank");
    }

    @DisplayName("Successful to register author if not registered")
    @Test
    void successfulToRegisterAuthorIfNotRegistered() {

        when(registerAuthorGateway.execute(mockRegisterAuthorGatewayIn())).thenReturn(mockRegisterAuthorGatewayOut());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(Optional.empty());

        final RegisterAuthorParameters parameters = new RegisterAuthorParameters("Joshua Bloch", "MALE", "1961-08-28");
        final Author author = getAuthorByIdUseCase.registerAuthor(parameters);

        assertThat(author).isEqualTo(mockRegisterAuthorGatewayOut());

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthdate()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Author mockRegisterAuthorGatewayIn() {
        return new Author(null, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }

    private Author mockRegisterAuthorGatewayOut() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }

    @DisplayName("Fail to register author if already registered")
    @Test
    void failToRegisterAuthorIfAlreadyRegistered() {

        when(registerAuthorGateway.execute(mockRegisterAuthorGatewayIn())).thenReturn(mockRegisterAuthorGatewayOut());
        when(getAuthorByNameGateway.execute("Joshua Bloch")).thenReturn(mockGetAuthorByNameGateway());

        final RegisterAuthorParameters registerAuthorParameters = new RegisterAuthorParameters("Joshua Bloch", "MALE", "1961-08-28");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> getAuthorByIdUseCase.registerAuthor(registerAuthorParameters));

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.author.name.already.exists");
    }

    private Optional<Author> mockGetAuthorByNameGateway() {
        return Optional.of(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
    }
}
