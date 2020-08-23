package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

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

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.usecase.RegisterAuthorUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.RegisterAuthorParameters;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.RegisterAuthorRequest;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Register Author")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RegisterAuthorController.class)
class RegisterAuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterAuthorUseCase registerAuthorUseCase;

    @DisplayName("Successful register author if not registered")
    @Test
    void successfulRegisterAuthorIfNotRegistered()
        throws Exception {

        when(registerAuthorUseCase.registerAuthor(mockRegisterAuthorUseCaseParameters())).thenReturn(mockRegisterAuthorUseCaseReturn());

        mockMvc.perform(post("/authors").content(buildRequestBody()).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.gender", is("MALE")))
               .andExpect(jsonPath("$.birthdate", is("1961-08-28")));

        verify(registerAuthorUseCase, times(1)).registerAuthor(mockRegisterAuthorUseCaseParameters());
        verifyNoMoreInteractions(registerAuthorUseCase);
    }

    private RegisterAuthorParameters mockRegisterAuthorUseCaseParameters() {
        return new RegisterAuthorParameters("Joshua Bloch", "MALE", "1961-08-28");
    }

    private Author mockRegisterAuthorUseCaseReturn() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final RegisterAuthorRequest request = new RegisterAuthorRequest("Joshua Bloch", "MALE", "1961-08-28");
        return new ObjectMapper().writeValueAsString(request);
    }
}
