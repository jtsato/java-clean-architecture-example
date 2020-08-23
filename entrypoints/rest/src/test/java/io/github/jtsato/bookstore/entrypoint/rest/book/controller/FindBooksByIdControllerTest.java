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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import io.github.jtsato.bookstore.core.book.usecase.FindBooksByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.FindAuthorsByIdsRequest;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Find Books By Id")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FindBooksByIdsController.class)
class FindBooksByIdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindBooksByIdsUseCase findBooksByIdsUseCase;

    @DisplayName("Successful find books by IDs if found")
    @Test
    void successfulFindBooksByIdsIfFound()
        throws Exception {

        when(findBooksByIdsUseCase.execute(mockFindBooksByIdsUseCaseParameters())).thenReturn(mockFindBooksByIdsUseCaseReturn());

        mockMvc.perform(post("/books/findByIds").content(buildRequestBody())
                                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.content[0].id", is(1)))
               .andExpect(jsonPath("$.content[0].author.id", is(1)))
               .andExpect(jsonPath("$.content[0].author.name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.content[0].author.gender", is("MALE")))
               .andExpect(jsonPath("$.content[0].author.birthdate", is("1961-08-28")))
               .andExpect(jsonPath("$.content[0].title", is("Effective Java (2nd Edition)")))
               .andExpect(jsonPath("$.content[0].price", is(10.00)))
               .andExpect(jsonPath("$.content[0].available", is(Boolean.TRUE)))
               .andExpect(jsonPath("$.content[0].creationDate", is("2020-02-29T12:00:00")))
               .andExpect(jsonPath("$.content[0].updateDate", is("2020-02-29T12:00:00")))
               .andExpect(jsonPath("$.pageable.page", is(1)))
               .andExpect(jsonPath("$.pageable.size", is(3)))
               .andExpect(jsonPath("$.pageable.numberOfElements", is(1)))
               .andExpect(jsonPath("$.pageable.totalOfElements", is(4)))
               .andExpect(jsonPath("$.pageable.totalPages", is(2)));

        verify(findBooksByIdsUseCase, times(1)).execute(mockFindBooksByIdsUseCaseParameters());
        verifyNoMoreInteractions(findBooksByIdsUseCase);
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final List<Long> ids = Arrays.asList(1L, 2L);
        final FindAuthorsByIdsRequest request = new FindAuthorsByIdsRequest(ids);
        return new ObjectMapper().writeValueAsString(request);
    }

    private List<Long> mockFindBooksByIdsUseCaseParameters() {
        return Arrays.asList(1L, 2L);
    }

    private Page<Book> mockFindBooksByIdsUseCaseReturn() {
        final Author author = new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));
        final List<Book> content = new ArrayList<>(1);
        content.add(new Book(1L,
                             author,
                             "Effective Java (2nd Edition)",
                             BigDecimal.valueOf(10.00),
                             Boolean.TRUE,
                             LocalDateTime.parse("2020-02-29T12:00:00"),
                             LocalDateTime.parse("2020-02-29T12:00:00")));
        return new PageImpl<>(content, new Pageable(1, 3, 1, 4L, 2));
    }
}
