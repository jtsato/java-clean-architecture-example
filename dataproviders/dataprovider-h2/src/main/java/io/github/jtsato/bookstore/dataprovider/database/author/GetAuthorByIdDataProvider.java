package io.github.jtsato.bookstore.dataprovider.database.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional(readOnly = true)
@Service
public class GetAuthorByIdDataProvider implements GetAuthorByIdGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> getAuthorById(final Long id) {

        final Optional<AuthorEntity> optional = authorRepository.findById(id);

        if (optional.isPresent()) {
            return Optional.of(AuthorMapper.of(optional.get()));
        }

        return Optional.empty();
    }
}
