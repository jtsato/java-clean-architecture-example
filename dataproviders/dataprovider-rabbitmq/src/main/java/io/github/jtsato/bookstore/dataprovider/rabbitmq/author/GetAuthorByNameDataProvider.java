package io.github.jtsato.bookstore.dataprovider.rabbitmq.author;

import java.util.Optional;

import org.springframework.stereotype.Service;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;

/**
 * @author Jorge Takeshi Sato  
 */

@Service
public class GetAuthorByNameDataProvider implements GetAuthorByNameGateway {

    @Override
    public Optional<Author> getAuthorByName(final String name) {
        return Optional.empty();
    }
}
