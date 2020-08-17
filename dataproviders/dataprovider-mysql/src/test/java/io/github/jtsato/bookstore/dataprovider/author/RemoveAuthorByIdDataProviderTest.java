package io.github.jtsato.bookstore.dataprovider.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DataJpaTest
@Import({RemoveAuthorByIdDataProvider.class})
@Sql("RemoveAuthorByIdDataProviderTest.sql")
class RemoveAuthorByIdDataProviderTest {

    @Autowired
    private RemoveAuthorByIdDataProvider removeAuthorByIdDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to remove author by id if found")
    @Test
    void successfulToRemoveAuthorByIdIfFound() {

        final Optional<Author> optional = removeAuthorByIdDataProvider.execute(3L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(2);
    }

    @DisplayName("Fail to remove author by id if not found")
    @Test
    void failToRemoveAuthorByIdIfNotFound() {

        final Optional<Author> optional = removeAuthorByIdDataProvider.execute(4L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }
}
