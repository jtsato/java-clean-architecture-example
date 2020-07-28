package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.RemoveBookByIdUseCase;
import io.github.jtsato.bookstore.entrypoint.rest.book.controller.impl.RemoveBookByIdControllerImpl;

/**
 * @author Jorge Takeshi Sato  
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RemoveBookByIdControllerImpl.class)
class RemoveBookByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RemoveBookByIdUseCase removeBookByIdUseCase;

    @DisplayName("Successful remove book by id if found")
    @Test
    void successfulRemoveBookByIdIfFound()
        throws Exception {

        when(removeBookByIdUseCase.removeBookById(1L)).thenReturn(mockRemoveBookByIdUseCaseReturn());

        mockMvc.perform(delete("/books/{id}", 1L).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isNoContent())
               .andExpect(content().string(""));

        verify(removeBookByIdUseCase, times(1)).removeBookById(1L);
        verifyNoMoreInteractions(removeBookByIdUseCase);
    }

    private Book mockRemoveBookByIdUseCaseReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
		return new Book(1L, author, "Effective Java (2nd Edition)", BigDecimal.valueOf(10.00), Boolean.TRUE, LocalDateTime.parse("2020-03-12T22:04:59.123"), LocalDateTime.parse("2020-03-12T22:04:59.123"), null);
    }
}
