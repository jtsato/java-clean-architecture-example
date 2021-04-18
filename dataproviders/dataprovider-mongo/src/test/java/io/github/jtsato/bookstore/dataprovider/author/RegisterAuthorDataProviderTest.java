package io.github.jtsato.bookstore.dataprovider.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.dataprovider.author.event.AuthorModelListener;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.common.SequenceGeneratorService;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Register Author")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({AuthorModelListener.class, SequenceGeneratorService.class, RegisterAuthorDataProvider.class})
class RegisterAuthorDataProviderTest {

    @Autowired
    private RegisterAuthorDataProvider registerAuthorDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void initialize() {
        authorRepository.deleteAll();
        assertThat(authorRepository.count()).isZero();
    }

    @DisplayName("Successful to register author if parameters are valid")
    @Test
    void successfulToRegisterAuthorIfParametersAreValid() {

        final Author newAuthor = new Author(null, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28"));

        final Author result = registerAuthorDataProvider.execute(newAuthor);

        assertThat(result.getId()).isNotNull();
        assertThat(result.getName()).isEqualTo(newAuthor.getName());
        assertThat(result.getGender()).isEqualTo(newAuthor.getGender());
        assertThat(result.getBirthdate()).isEqualTo(newAuthor.getBirthdate());

        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to register author if parameters are not valid")
    @Test
    void failToRegisterAuthorIfParametersAreNotValid() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            final Author newAuthor = new Author(null, null, Gender.MALE, null);
            registerAuthorDataProvider.execute(newAuthor);
        });

        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class);
    }
}
