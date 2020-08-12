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
import java.util.ArrayList;
import java.util.List;

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
import io.github.jtsato.bookstore.core.author.usecase.SearchAuthorsUseCase;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SearchAuthorsController.class)
class SearchAuthorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchAuthorsUseCase searchAuthorsUseCase;

    @DisplayName("Successful search authors if found")
    @Test
    void successfulSearchAuthorsIfFound()
        throws Exception {

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, "John", "MALE", "1960-01-01", "1980-12-31");
        when(searchAuthorsUseCase.searchAuthors(parameters, 1, 3, "name: ASC")).thenReturn(mockSearchAuthorsUseCaseReturn());

        final String queryParameters = "page=1&size=3&sort=name,asc&name=John&gender=MALE&startBirthdate=1960-01-01&endBirthdate=1980-12-31";

        mockMvc.perform(get(String.format("/authors/?%s", queryParameters)).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                           .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.content[0].id", is(1)))
               .andExpect(jsonPath("$.content[0].name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.content[0].gender", is("MALE")))
               .andExpect(jsonPath("$.content[0].birthdate", is("1961-08-28")))
               .andExpect(jsonPath("$.pageable.page", is(1)))
               .andExpect(jsonPath("$.pageable.size", is(3)))
               .andExpect(jsonPath("$.pageable.numberOfElements", is(1)))
               .andExpect(jsonPath("$.pageable.totalOfElements", is(4)))
               .andExpect(jsonPath("$.pageable.totalPages", is(2)));

        verify(searchAuthorsUseCase, times(1)).searchAuthors(parameters, 1, 3, "name: ASC");
        verifyNoMoreInteractions(searchAuthorsUseCase);
    }

    private Page<Author> mockSearchAuthorsUseCaseReturn() {

        final List<Author> content = new ArrayList<>(1);
        content.add(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));

        return new PageImpl<>(content, new Pageable(1, 3, 1, 4L, 2));
    }
}
