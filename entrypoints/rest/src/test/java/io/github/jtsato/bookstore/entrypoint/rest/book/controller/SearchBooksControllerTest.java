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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
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

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.SearchBooksUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SearchBooksController.class)
class SearchBooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchBooksUseCase searchBooksUseCase;

    @DisplayName("Successful search books if found")
    @Test
    void successfulSearchBooksIfFound()
        throws Exception {

        final SearchAuthorsParameters searchAuthorsParameters = new SearchAuthorsParameters(1L, null, null, null, null);
        final ImmutablePair<BigDecimal, BigDecimal> prices = new ImmutablePair<>(null, null);
        final ImmutablePair<String, String> creationDates = new ImmutablePair<>("2020-02-29T00:00:00", "2020-02-29T23:59:59");
        final ImmutablePair<String, String> updateDates = new ImmutablePair<>(null, null);
        final SearchBooksParameters parameters = new SearchBooksParameters(searchAuthorsParameters, "Effective Java", prices, null, creationDates, updateDates);

        when(searchBooksUseCase.execute(parameters, 1, 3, "title: ASC")).thenReturn(mockSearchBooksUseCaseReturn());

        final String queryParameters = "page=1&size=3&sort=title,asc&title=Effective Java&author.id=1&startCreationDate=2020-02-29T00:00:00&endCreationDate=2020-02-29T23:59:59";

        mockMvc.perform(get(String.format("/books/?%s", queryParameters)).contentType(MediaType.APPLICATION_JSON_VALUE)
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

        verify(searchBooksUseCase, times(1)).execute(parameters, 1, 3, "title: ASC");
        verifyNoMoreInteractions(searchBooksUseCase);
    }

    private Page<Book> mockSearchBooksUseCaseReturn() {

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
