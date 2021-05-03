package io.github.jtsato.bookstore.infra.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Author By Name")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({GetAuthorByNameDataProvider.class})
@Sql("GetAuthorByNameDataProviderTest.sql")
class GetAuthorByNameDataProviderTest {

    @Autowired
    private GetAuthorByNameDataProvider getAuthorByNameDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to get author by name if found")
    @Test
    void successfulToGetAuthorByNameIfFound() {

        final Optional<Author> optional = getAuthorByNameDataProvider.execute("Cay S. Horstmann");

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(2);
    }

    @DisplayName("Fail to get author by name if not found")
    @Test
    void failToGetAuthorByNameIfNotFound() {

        final Optional<Author> optional = getAuthorByNameDataProvider.execute("Robert Cecil Martin");

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(2);
    }
}