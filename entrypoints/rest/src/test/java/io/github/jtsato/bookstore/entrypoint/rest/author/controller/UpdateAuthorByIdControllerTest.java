package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
import io.github.jtsato.bookstore.core.author.usecase.UpdateAuthorByIdUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.UpdateAuthorByIdParameters;
import io.github.jtsato.bookstore.entrypoint.rest.author.controller.impl.UpdateAuthorByIdControllerImpl;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.UpdateAuthorByIdRequest;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UpdateAuthorByIdControllerImpl.class)
class UpdateAuthorByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateAuthorByIdUseCase updateAuthorByIdUseCase;

    @DisplayName("Successful update author by if found")
    @Test
    void successfulUpdateAuthorByIdIfFound()
        throws Exception {

        when(updateAuthorByIdUseCase.updateAuthorById(mockUpdateAuthorByIdUseCaseParameters())).thenReturn(mockUpdateAuthorByIdUseCaseReturn());

        mockMvc.perform(put("/authors/{id}", 1L).content(buildRequestBody())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.gender", is("MALE")))
               .andExpect(jsonPath("$.birthday", is("1961-08-28")));

        verify(updateAuthorByIdUseCase, times(1)).updateAuthorById(mockUpdateAuthorByIdUseCaseParameters());
        verifyNoMoreInteractions(updateAuthorByIdUseCase);
    }

    private UpdateAuthorByIdParameters mockUpdateAuthorByIdUseCaseParameters() {
        return new UpdateAuthorByIdParameters(1L, "Joshua Bloch", "MALE", "1961-08-28");
    }

    private Author mockUpdateAuthorByIdUseCaseReturn() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final UpdateAuthorByIdRequest request = new UpdateAuthorByIdRequest("Joshua Bloch", "MALE", "1961-08-28");
        return new ObjectMapper().writeValueAsString(request);
    }
}
