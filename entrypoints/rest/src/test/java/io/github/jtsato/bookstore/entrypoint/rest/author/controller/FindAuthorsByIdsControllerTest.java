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
import io.github.jtsato.bookstore.core.author.usecase.FindAuthorsByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.entrypoint.rest.author.domain.request.FindAuthorsByIdsRequest;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Find Authors By Ids")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = FindAuthorsByIdsController.class)
class FindAuthorsByIdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FindAuthorsByIdsUseCase findAuthorsByIdsUseCase;

    @DisplayName("Successful find authors by IDs if found")
    @Test
    void successfulFindAuthorsByIdsIfFound()
        throws Exception {

        when(findAuthorsByIdsUseCase.findAuthorsByIds(mockFindAuthorsByIdsUseCaseParameters())).thenReturn(mockFindAuthorsByIdsUseCaseReturn());

        mockMvc.perform(post("/authors/findByIds").content(buildRequestBody())
                                                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                                                  .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.content[0].id", is(1)))
               .andExpect(jsonPath("$.content[0].name", is("Joshua Bloch")))
               .andExpect(jsonPath("$.content[0].gender", is("MALE")))
               .andExpect(jsonPath("$.content[0].birthdate", is("1961-08-28")))
               .andExpect(jsonPath("$.pageable.page", is(0)))
               .andExpect(jsonPath("$.pageable.size", is(2)))
               .andExpect(jsonPath("$.pageable.numberOfElements", is(1)))
               .andExpect(jsonPath("$.pageable.totalOfElements", is(1)))
               .andExpect(jsonPath("$.pageable.totalPages", is(1)));

        verify(findAuthorsByIdsUseCase, times(1)).findAuthorsByIds(mockFindAuthorsByIdsUseCaseParameters());
        verifyNoMoreInteractions(findAuthorsByIdsUseCase);
    }

    private String buildRequestBody()
        throws JsonProcessingException {
        final List<Long> ids = Arrays.asList(1L, 2L);
        final FindAuthorsByIdsRequest request = new FindAuthorsByIdsRequest(ids);
        return new ObjectMapper().writeValueAsString(request);
    }

    private List<Long> mockFindAuthorsByIdsUseCaseParameters() {
        return Arrays.asList(1L, 2L);
    }

    private Page<Author> mockFindAuthorsByIdsUseCaseReturn() {
        final List<Author> content = new ArrayList<>(1);
        content.add(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));
        return new PageImpl<>(content, new Pageable(0, 2, 1, 1L, 1));
    }
}
