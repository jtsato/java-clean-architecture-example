package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.usecase.GetAuthorByIdUseCase;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetAuthorByIdController.class)
class GetAuthorByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetAuthorByIdUseCase getAuthorByIdUseCase;

    @DisplayName("Successful get author by id if found")
    @Test
    void successfulGetAuthorByIdIfFound()
        throws Exception {

        when(getAuthorByIdUseCase.getAuthorById(1L)).thenReturn(mockGetAuthorByIdUseCaseReturn());

        mockMvc.perform(get("/authors/{id}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.gender", is("MALE")))
               .andExpect(jsonPath("$.birthday", is("1961-08-28")));

        verify(getAuthorByIdUseCase, times(1)).getAuthorById(1L);
        verifyNoMoreInteractions(getAuthorByIdUseCase);
    }

    private Author mockGetAuthorByIdUseCaseReturn() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }
}
