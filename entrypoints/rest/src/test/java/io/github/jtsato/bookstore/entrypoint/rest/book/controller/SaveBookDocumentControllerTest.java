package io.github.jtsato.bookstore.entrypoint.rest.book.controller;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Base64;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.usecase.SaveBookDocumentUseCase;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SaveBookDocumentParameters;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Save Book Document")
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = SaveBookDocumentController.class)
class SaveBookDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SaveBookDocumentUseCase saveBookDocumentUseCase;

    @DisplayName("Successful save book document if not registered")
    @Test
    void successfulSaveBookDocumentIfNotRegistered()
        throws Exception {

        when(saveBookDocumentUseCase.execute(mockSaveBookDocumentUseCaseParameters())).thenReturn(mockSaveBookDocumentUseCaseReturn());

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/books/{bookId}/content", 1L).file(buildRequestBody());

        mockMvc.perform(builder)
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(jsonPath("$.id", is(1)))
               .andExpect(jsonPath("$.bookId", is(1)))
               .andExpect(jsonPath("$.contentType", is("text/plain")))
               .andExpect(jsonPath("$.extension", is("txt")))
               .andExpect(jsonPath("$.name", is("file")))
               .andExpect(jsonPath("$.size", is(27)))
               .andExpect(jsonPath("$.creationDate", is("2020-03-12T22:04:59.123")))
               .andExpect(jsonPath("$.updateDate", is("2020-03-12T22:04:59.123")));

        verify(saveBookDocumentUseCase, times(1)).execute(mockSaveBookDocumentUseCaseParameters());
        verifyNoMoreInteractions(saveBookDocumentUseCase);
    }

    private SaveBookDocumentParameters mockSaveBookDocumentUseCaseParameters() {
        return new SaveBookDocumentParameters(1L,
                                              "text/plain",
                                              "txt",
                                              "file",
                                              27L,
                                              Base64.getEncoder().encodeToString("Lorem ipsum dolor sit amet.".getBytes()));
    }

    private BookDocument mockSaveBookDocumentUseCaseReturn() {
        return new BookDocument(1L,
                                1L,
                                "text/plain",
                                "txt",
                                "file",
                                27L,
                                "Lorem ipsum dolor sit amet.",
                                LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                LocalDateTime.parse("2020-03-12T22:04:59.123"));
    }

    private MockMultipartFile buildRequestBody() {
        return new MockMultipartFile("file", "Document.txt", "text/plain", "Lorem ipsum dolor sit amet.".getBytes());
    }
}
