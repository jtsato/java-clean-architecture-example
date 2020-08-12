package io.github.jtsato.bookstore.dataprovider.author;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;

/**
 * @author Jorge Takeshi Sato
 */

@Service
public class GetAuthorByIdDataProvider implements GetAuthorByIdGateway {

    @Override
    public Optional<Author> getAuthorById(final Long id) {
        return Optional.of(new Author(id, null, Gender.FEMALE, LocalDate.MIN));
    }
}
