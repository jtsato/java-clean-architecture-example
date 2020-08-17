package io.github.jtsato.bookstore.dataprovider.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.dataprovider.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DataJpaTest
@Import({GetBookDocumentByBookIdDataProvider.class})
@Sql("GetBookDocumentByBookIdDataProviderTest.sql")
class GetBookDocumentByBookIdDataProviderTest {

    @Autowired
    private GetBookDocumentByBookIdDataProvider getBookDocumentByBookIdDataProvider;

    @Autowired
    private BookRepository authorRepository;

    @DisplayName("Successful to get book document by id if found")
    @Test
    void successfulToGetBookDocumentByBookIdIfFound() {

        final Optional<BookDocument> optional = getBookDocumentByBookIdDataProvider.execute(1L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(1);
    }

    @DisplayName("Fail to get book document by id if not found")
    @Test
    void failToGetAuthorByTitleIfNotFound() {

        final Optional<BookDocument> optional = getBookDocumentByBookIdDataProvider.execute(2L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(1);
    }
}
