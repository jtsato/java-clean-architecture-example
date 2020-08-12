package io.github.jtsato.bookstore.dataprovider.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookDocumentRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DataJpaTest
@Import({SaveBookDocumentDataProvider.class})
class SaveBookDocumentDataProviderTest {

    @Autowired
    private SaveBookDocumentDataProvider saveBookDocumentDataProvider;

    @Autowired
    private BookDocumentRepository bookDocumentRepository;

    @DisplayName("Successful to save book document if parameters are valid")
    @Test
    void successfulToSaveBookDocumentIfParametersAreValid() {

        final BookDocument bookDocument = new BookDocument(null,
                                                           1L,
                                                           "text/plain",
                                                           "txt",
                                                           "Document",
                                                           123L,
                                                           "Lorem ipsum dolor sit amet.",
                                                           LocalDateTime.parse("2020-03-12T22:04:59.123"),
                                                           LocalDateTime.parse("2020-04-12T22:04:59.123"));

        final BookDocument result = saveBookDocumentDataProvider.saveBookDocument(bookDocument);

        assertThat(result.getBookId()).isEqualTo(1L);
        assertThat(result.getContentType()).isEqualTo("text/plain");
        assertThat(result.getExtension()).isEqualTo("txt");
        assertThat(result.getName()).isEqualTo("Document");
        assertThat(result.getSize()).isEqualTo(123L);
        assertThat(result.getContent()).isEqualTo("Lorem ipsum dolor sit amet.");
        assertThat(result.getCreationDate()).isEqualTo(LocalDateTime.parse("2020-03-12T22:04:59.123"));
        assertThat(result.getUpdateDate()).isEqualTo(LocalDateTime.parse("2020-04-12T22:04:59.123"));

        assertThat(bookDocumentRepository.count()).isOne();
    }

    @DisplayName("Failt to save book document if parameters are not valid")
    @Test
    void failtToSaveBookDocumentIfParametersAreNotValid() {

        final BookDocument bookDocument = new BookDocument(null, null, null, null, null, null, null, null, null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> saveBookDocumentDataProvider.saveBookDocument(bookDocument));

        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class);
    }
}
