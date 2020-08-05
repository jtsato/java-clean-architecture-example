package io.github.jtsato.bookstore.dataprovider.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({FindAuthorsByIdsDataProvider.class})
@Sql("FindAuthorsByIdsDataProviderTest.sql")
class FindAuthorsByIdsDataProviderTest {

    @Autowired
    private FindAuthorsByIdsDataProvider findAuthorsByIdsDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to find authors by IDs if found")
    @Test
    void successfulToFindAuthorsByIdsIfFound() {

        final List<Long> ids = Arrays.asList(1L, 2L);

        final Page<Author> pageOfAuthors = findAuthorsByIdsDataProvider.findAuthorsByIds(ids);

        assertThat(pageOfAuthors).isNotNull();
        assertThat(pageOfAuthors.getContent()).isNotNull().isNotEmpty();

        assertThat(authorRepository.count()).isEqualTo(2L);
    }

    @DisplayName("Fail to find authors by IDs if not found")
    @Test
    void failToFindAuthorsByIdsIfNotFound() {

        final List<Long> ids = Arrays.asList(3L, 4L);

        final Page<Author> pageOfAuthors = findAuthorsByIdsDataProvider.findAuthorsByIds(ids);

        assertThat(pageOfAuthors).isNotNull();
        assertThat(pageOfAuthors.getContent()).isNotNull().isEmpty();

        assertThat(authorRepository.count()).isEqualTo(2L);
    }
}
