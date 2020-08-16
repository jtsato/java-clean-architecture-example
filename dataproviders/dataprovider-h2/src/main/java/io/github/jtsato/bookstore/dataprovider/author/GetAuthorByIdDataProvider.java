package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
public class GetAuthorByIdDataProvider implements GetAuthorByIdGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> execute(final Long id) {
        final Optional<AuthorEntity> optional = authorRepository.findById(id);
        return optional.map(AuthorMapper::of);
    }
}
