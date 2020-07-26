package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.GetBookByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl.GetBookByIdControllerImpl;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = GetBookByIdControllerImpl.class)
class GetBookByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetBookByIdUseCase getBookByIdUseCase;

    @DisplayName("Successful get book by id if found")
    @Test
    void successfulGetBookByIdIfFound()
        throws Exception {

        when(getBookByIdUseCase.getBookById(1L)).thenReturn(mockGetBookByIdUseCaseReturn());

        mockMvc.perform(get("/books/{id}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.title", is("Effective Java (2nd Edition)")))
               .andExpect(jsonPath("$.creationDate", is("2020-03-12T22:04:59.123")))
               .andExpect(jsonPath("$.author.id", is(1)))
               .andExpect(jsonPath("$.author.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.author.gender", is("MALE")))
               .andExpect(jsonPath("$.author.birthday", is("1961-08-28")));

        verify(getBookByIdUseCase, times(1)).getBookById(1L);
        verifyNoMoreInteractions(getBookByIdUseCase);
    }

    private Book mockGetBookByIdUseCaseReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        return new Book(1L, "Effective Java (2nd Edition)", LocalDateTime.parse("2020-03-12T22:04:59.123"), author);
    }
}
