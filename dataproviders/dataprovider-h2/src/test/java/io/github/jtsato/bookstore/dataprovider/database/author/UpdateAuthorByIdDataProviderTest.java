package io.github.jtsato.bookstore.dataprovider.database.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

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

        final Author author1 = new Author(4L, "Kathy Sierra", Gender.FEMALE, LocalDate.parse("1957-01-01"));
        
        final Author author2 = updateAuthorById(author1);
        
        assertThat(author2).isEqualToComparingFieldByField(author1);

        assertThat(authorRepository.count()).isEqualTo(4L);
    }

    private Author updateAuthorById(final Author author) {
        
        final Optional<Author> optional = updateAuthorByIdDataProvider.updateAuthorById(author);

        assertThat(optional).isPresent();
        
        return optional.get();
    }

    @DisplayName("Fail to update author by id if not found")    
    @Test
    void failToUpdateAuthorByIdIfNotFound() {

        final Optional<Author> optional = updateAuthorByIdDataProvider.updateAuthorById(new Author(5L, null, Gender.MALE, null));

        assertThat(optional).isNotPresent();
    }
}
