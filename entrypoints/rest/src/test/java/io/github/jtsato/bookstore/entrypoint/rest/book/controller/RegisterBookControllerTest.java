package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.RegisterBookUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.RegisterBookParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl.RegisterBookControllerImpl;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.RegisterBookRequest;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegisterBookControllerImpl.class)
class RegisterBookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterBookUseCase registerBookUseCase;

    @DisplayName("Successful register book if not registered")
    @Test
    void successfulRegisterBookIfNotRegistered()
        throws Exception {

        when(registerBookUseCase.registerBook(mockRegisterBookUseCaseParameters())).thenReturn(mockRegisterBookUseCaseReturn());

        mockMvc.perform(post("/books").content(buildRequestBody()).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.author.id", is(1)))
               .andExpect(jsonPath("$.author.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.author.gender", is("MALE")))
               .andExpect(jsonPath("$.author.birthday", is("1961-08-28")))
               .andExpect(jsonPath("$.title", is("Effective Java (2nd Edition)")))
               .andExpect(jsonPath("$.price", is(10.00)))
               .andExpect(jsonPath("$.available", is(Boolean.TRUE)))
               .andExpect(jsonPath("$.creationDate", is("2020-02-29T12:00:00")))
               .andExpect(jsonPath("$.updateDate", is("2020-02-29T12:00:00")));

        verify(registerBookUseCase, times(1)).registerBook(mockRegisterBookUseCaseParameters());
        verifyNoMoreInteractions(registerBookUseCase);
    }

    private RegisterBookParameters mockRegisterBookUseCaseParameters() {
        return new RegisterBookParameters(null, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE);
    }

    private Book mockRegisterBookUseCaseReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L, author, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE, LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"), null);
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final RegisterBookRequest request = new RegisterBookRequest(1l, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE);
        return new ObjectMapper().writeValueAsString(request);
    }
}
