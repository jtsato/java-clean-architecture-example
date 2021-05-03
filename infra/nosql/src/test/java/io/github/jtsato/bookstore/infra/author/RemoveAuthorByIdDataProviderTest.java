package io.github.jtsato.bookstore.infra.author;

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
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Remove Author By Id")
@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import({RemoveAuthorByIdProvider.class})
class RemoveAuthorByIdProviderTest {

    @Autowired
    private RemoveAuthorByIdProvider removeAuthorByIdProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to remove author by id if found")
    @Test
    void successfulToRemoveAuthorByIdIfFound() {

        final Optional<Author> optional = removeAuthorByIdProvider.execute(3L);

        assertThat(optional).isPresent();
        assertThat(authorRepository.count()).isEqualTo(2);
    }

    @DisplayName("Fail to remove author by id if not found")
    @Test
    void failToRemoveAuthorByIdIfNotFound() {

        final Optional<Author> optional = removeAuthorByIdProvider.execute(4L);

        assertThat(optional).isNotPresent();
        assertThat(authorRepository.count()).isEqualTo(3);
    }
}
