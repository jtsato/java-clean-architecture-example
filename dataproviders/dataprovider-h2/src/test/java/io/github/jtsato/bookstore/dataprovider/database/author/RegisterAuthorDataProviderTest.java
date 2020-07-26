package io.github.jtsato.bookstore.dataprovider.database.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({RegisterAuthorDataProvider.class})
class RegisterAuthorDataProviderTest {

    @Autowired
    private RegisterAuthorDataProvider registerAuthorDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to register author if parameters are valid")
    @Test
    void successfulToRegisterAuthorIfParametersAreValid () {

        final Author author = registerAuthorDataProvider.registerAuthor(new Author(null, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));

        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthday()).isEqualTo(LocalDate.parse("1961-08-28"));
        
        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to register author if parameters are not valid")
    @Test
    void failToRegisterAuthorIfParametersAreNotValid() {
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            registerAuthorDataProvider.registerAuthor(new Author(null, null, Gender.MALE, null));
        });   
        
        assertThat(exception).isInstanceOf(DataIntegrityViolationException.class);
    }
}
