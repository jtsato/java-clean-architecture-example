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

@DisplayName("Get Author By Id")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({GetAuthorByIdProvider.class})
@Sql("GetAuthorByIdProviderTest.sql")
class GetAuthorByIdProviderTest {

    @Autowired
    private GetAuthorByIdProvider getAuthorByIdProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to get author by id if found")
    @Test
    void successfulToGetAuthorByIdIfFound() {

        final Optional<Author> optional = getAuthorByIdProvider.execute(1L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to get author by id if not found")
    @Test
    void failToGetAuthorByIdIfNotFound() {

        final Optional<Author> optional = getAuthorByIdProvider.execute(2L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isOne();
    }
}
