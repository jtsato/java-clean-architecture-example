package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class GetAuthorByNameDataProvider implements GetAuthorByNameGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> execute(final String name) {
        final Optional<AuthorEntity> optional = authorRepository.findByName(name);
        return optional.map(AuthorMapper::of);
    }
}
