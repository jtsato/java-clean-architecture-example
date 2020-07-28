package io.github.jtsato.bookstore.core.book.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.book.domain.BookContent;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookContentGateway;
import io.github.jtsato.bookstore.core.book.usecase.impl.RegisterBookContentUseCaseImpl;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookContentParameters;
import io.github.jtsato.bookstore.core.common.GetLocalDateTime;
import io.github.jtsato.bookstore.core.exception.NotFoundException;
import io.github.jtsato.bookstore.core.exception.UniqueConstraintException;



/**
 * @author Jorge Takeshi Sato  
 */

class RegisterBookContentUseCaseTest {

    @Mock
    private RegisterBookContentGateway registerBookContentGateway = Mockito.mock(RegisterBookContentGateway.class);

    @Mock
    private GetLocalDateTime getLocalDateTime = Mockito.mock(GetLocalDateTime.class);
    
    // TODO: Upsert

    @InjectMocks
    private RegisterBookContentUseCase getBookContentByIdUseCase = new RegisterBookContentUseCaseImpl(registerBookContentGateway, getLocalDateTime);

    @DisplayName("Fail to register a book content if parameters are not valid")
    @Test
    void failToRegisterABookContentIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new RegisterBookContentParameters(null, StringUtils.SPACE);
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("authorId: validation.author.id.null");
        assertThat(constraintViolationException.getMessage()).contains("content: validation.book.content.blank");
    }
    
    @DisplayName("Successful to register book content if not registered")
    @Test
    void successfulToRegisterBookContentIfNotRegistered() {

        when(registerBookContentGateway.registerBookContent(mockRegisterBookContentGatewayParameters())).thenReturn(mockRegisterBookContentGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookContentParameters registerBookContentParameters = new RegisterBookContentParameters(1L, "Effective Java (2nd Edition)");
        final BookContent bookContent = getBookContentByIdUseCase.registerBookContent(registerBookContentParameters);

        assertThat(bookContent.getId()).isEqualTo(1L);
        assertThat(bookContent.getContent()).isEqualTo("Effective Java (2nd Edition)");
        assertThat(bookContent.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private BookContent mockRegisterBookContentGatewayParameters() {
        return new BookContent(null, 1L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private BookContent mockRegisterBookContentGatewayReturn() {
        return new BookContent(1L, 1L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    @DisplayName("Fail to register book content if already registered")
    @Test
    void failToRegisterBookContentIfAlreadyRegistered() {

        when(registerBookContentGateway.registerBookContent(mockRegisterBookContentGatewayParameters())).thenReturn(mockRegisterBookContentGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookContentParameters registerBookContentParameters = new RegisterBookContentParameters(1L, "Effective Java (2nd Edition)");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookContentByIdUseCase.registerBookContent(registerBookContentParameters);
        });

        assertThat(exception).isInstanceOf(UniqueConstraintException.class);
        
        final UniqueConstraintException uniqueConstraintException = (UniqueConstraintException) exception;
        assertThat(uniqueConstraintException.getArgs()).isNotEmpty();
        assertThat(uniqueConstraintException.getMessage()).isEqualTo("validation.bookContent.title.already.exists");
    }

    @DisplayName("Fail to register bookContent if author not found")
    @Test
    void failToRegisterBookContentIfAuthorNotFound() {

        when(registerBookContentGateway.registerBookContent(mockRegisterBookContentGatewayParameters())).thenReturn(mockRegisterBookContentGatewayReturn());
        when(getLocalDateTime.now()).thenReturn(LocalDateTime.parse("2020-03-12T22:04:59.123"));

        final RegisterBookContentParameters registerBookContentParameters = new RegisterBookContentParameters(1L, "Effective Java (2nd Edition)");

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            getBookContentByIdUseCase.registerBookContent(registerBookContentParameters);
        });

        assertThat(exception).isInstanceOf(NotFoundException.class);
        final NotFoundException notFoundException = (NotFoundException) exception;
        assertThat(notFoundException.getArgs()).isNotEmpty();
        assertThat(notFoundException.getMessage()).isEqualTo("validation.author.id.notfound");
    }
}
