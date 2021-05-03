package io.github.jtsato.bookstore.infra.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Update Author By Id")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Import({UpdateAuthorByIdDataProvider.class})
@Sql("UpdateAuthorByIdDataProviderTest.sql")
class UpdateAuthorByIdDataProviderTest {

    @Autowired
    private UpdateAuthorByIdDataProvider updateAuthorByIdDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to update author by id if found")
    @Test
    void successfulToUpdateAuthorByIdIfFound() {

        final Author update = new Author(4L, "Kathy Sierra", Gender.FEMALE, LocalDate.parse("1957-01-01"));

        final Author result = updateAuthorById(update);

        assertThat(result.getId()).isEqualTo(update.getId());
        assertThat(result.getName()).isEqualTo(update.getName());
        assertThat(result.getGender()).isEqualTo(update.getGender());
        assertThat(result.getBirthdate()).isEqualTo(update.getBirthdate());

        assertThat(authorRepository.count()).isEqualTo(4L);
    }

    private Author updateAuthorById(final Author author) {

        final Optional<Author> optional = updateAuthorByIdDataProvider.execute(author);

        assertThat(optional).isPresent();

        return optional.get();
    }

    @DisplayName("Fail to update author by id if not found")
    @Test
    void failToUpdateAuthorByIdIfNotFound() {

        final Optional<Author> optional = updateAuthorByIdDataProvider.execute(new Author(5L, null, Gender.MALE, null));

        assertThat(optional).isNotPresent();
    }
}
