package io.github.jtsato.bookstore.dataprovider.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Get Author By Id")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({GetAuthorByIdDataProvider.class})
class GetAuthorByIdDataProviderTest {

    @Autowired
    private GetAuthorByIdDataProvider getAuthorByIdDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to get author by id if found")
    @Test
    void successfulToGetAuthorByIdIfFound() {

        final Optional<Author> optional = getAuthorByIdDataProvider.execute(1L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isOne();
    }

    @DisplayName("Fail to get author by id if not found")
    @Test
    void failToGetAuthorByIdIfNotFound() {

        final Optional<Author> optional = getAuthorByIdDataProvider.execute(2L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isOne();
    }
}
