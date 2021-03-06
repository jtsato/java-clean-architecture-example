package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.UpdateBookByIdUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.UpdateBookByIdParameters;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.request.UpdateBookByIdRequest;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Update Book By Id")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UpdateBookByIdController.class)
class UpdateBookByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UpdateBookByIdUseCase updateBookByIdUseCase;

    @DisplayName("Successful update book by id if found")
    @Test
    void successfulUpdateBookByIdIfFound()
        throws Exception {

        when(updateBookByIdUseCase.execute(mockUpdateBookByIdUseCaseParameters())).thenReturn(mockUpdateBookByIdUseCaseReturn());

        mockMvc.perform(put("/books/{id}", 1L).content(buildRequestBody())
                                              .contentType(MediaType.APPLICATION_JSON_VALUE)
                                              .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.author.id", is(1)))
               .andExpect(jsonPath("$.author.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.author.gender", is("MALE")))
               .andExpect(jsonPath("$.author.birthdate", is("1961-08-28")))
               .andExpect(jsonPath("$.title", is("Effective Java (2nd Edition)")))
               .andExpect(jsonPath("$.price", is(10.00)))
               .andExpect(jsonPath("$.available", is(Boolean.TRUE)))
               .andExpect(jsonPath("$.creationDate", is("2020-03-12T22:04:59.123")))
               .andExpect(jsonPath("$.updateDate", is("2020-04-12T22:04:59.123")));

        verify(updateBookByIdUseCase, times(1)).execute(mockUpdateBookByIdUseCaseParameters());
        verifyNoMoreInteractions(updateBookByIdUseCase);
    }

    private UpdateBookByIdParameters mockUpdateBookByIdUseCaseParameters() {
        return new UpdateBookByIdParameters(1L, 1L, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE);
    }

    private Book mockUpdateBookByIdUseCaseReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L,
                        author,
                        "Effective Java (2nd Edition)",
                        BigDecimal.valueOf(10.00),
                        Boolean.TRUE,
                        LocalDateTime.parse("2020-03-12T22:04:59.123"),
                        LocalDateTime.parse("2020-04-12T22:04:59.123"));
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final UpdateBookByIdRequest request = new UpdateBookByIdRequest(1L, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE);
        return new ObjectMapper().writeValueAsString(request);
    }
}
