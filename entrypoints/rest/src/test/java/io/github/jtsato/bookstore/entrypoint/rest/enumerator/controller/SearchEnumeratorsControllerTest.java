package io.github.jtsato.bookstore.entrypoint.rest.enumerator.controller;


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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.usecase.SearchEnumeratorsUseCase;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.domain.response.EnumeratorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.enumerator.mapper.SearchEnumeratorsPresenter;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Search Enumerators")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SearchEnumeratorsController.class)
class SearchEnumeratorsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchEnumeratorsUseCase searchEnumeratorsUseCase;

    @MockBean
    private SearchEnumeratorsPresenter searchEnumeratorsPresenter;

    @DisplayName("Successful search books if found")
    @Test
    void successfulSearchEnumeratorsIfFound()
        throws Exception {

        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(null, null);
        when(searchEnumeratorsUseCase.execute(parameters)).thenReturn(mockSearchEnumeratorsUseCaseReturn());
        when(searchEnumeratorsPresenter.of(mockSearchEnumeratorsUseCaseReturn())).thenReturn(mockSearchEnumeratorsPresenterReturn());

        mockMvc.perform(get(String.format("/enumerators/?%s", StringUtils.EMPTY)).contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                                 .accept(MediaType.APPLICATION_JSON_VALUE))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$[0].domain", is("Gender")))
               .andExpect(jsonPath("$[0].key", is("MALE")))
               .andExpect(jsonPath("$[0].value", is("Male")))
               .andExpect(jsonPath("$[1].domain", is("Gender")))
               .andExpect(jsonPath("$[1].key", is("FEMALE")))
               .andExpect(jsonPath("$[1].value", is("Female")));

        verify(searchEnumeratorsUseCase, times(1)).execute(parameters);
        verifyNoMoreInteractions(searchEnumeratorsUseCase);
    }

    private List<Enumerator> mockSearchEnumeratorsUseCaseReturn() {
        final List<Enumerator> elements = new ArrayList<>(2);
        elements.add(new Enumerator("Gender", Gender.MALE.name(), Gender.MALE.getMessageKey()));
        elements.add(new Enumerator("Gender", Gender.FEMALE.name(), Gender.FEMALE.getMessageKey()));
        return elements;
    }

    private List<EnumeratorResponse> mockSearchEnumeratorsPresenterReturn() {
        final List<EnumeratorResponse> elements = new ArrayList<>(2);
        elements.add(new EnumeratorResponse("Gender", Gender.MALE.name(), "Male"));
        elements.add(new EnumeratorResponse("Gender", Gender.FEMALE.name(), "Female"));
        return elements;
    }
}
