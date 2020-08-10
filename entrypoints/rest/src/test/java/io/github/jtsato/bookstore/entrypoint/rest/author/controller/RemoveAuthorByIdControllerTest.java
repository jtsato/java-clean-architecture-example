package io.github.jtsato.bookstore.entrypoint.rest.author.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import io.github.jtsato.bookstore.core.author.usecase.RemoveAuthorByIdUseCase;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RemoveAuthorByIdController.class)
class RemoveAuthorByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RemoveAuthorByIdUseCase removeAuthorByIdUseCase;

    @DisplayName("Successful remove author by id if found")
    @Test
    void successfulRemoveAuthorByIdIfFound()
        throws Exception {

        when(removeAuthorByIdUseCase.removeAuthorById(1L)).thenReturn(mockRemoveAuthorByIdUseCaseReturn());

        mockMvc.perform(delete("/authors/{id}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andExpect(content().string(""));

        verify(removeAuthorByIdUseCase, times(1)).removeAuthorById(1L);
        verifyNoMoreInteractions(removeAuthorByIdUseCase);
    }

    private Author mockRemoveAuthorByIdUseCaseReturn() {
        return new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
    }
}
