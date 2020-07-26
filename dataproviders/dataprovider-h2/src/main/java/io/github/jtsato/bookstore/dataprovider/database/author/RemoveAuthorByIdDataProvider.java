package io.github.jtsato.bookstore.dataprovider.database.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class RemoveAuthorByIdDataProvider implements RemoveAuthorByIdGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> removeAuthorById(final Long id) {

        final Optional<AuthorEntity> optional = authorRepository.findById(id);

        if (optional.isPresent()) {
            final AuthorEntity authorEntity = optional.get();
            final Optional<Author> optionalOfAuthor = Optional.of(AuthorMapper.of(authorEntity));
            authorRepository.delete(authorEntity);
            return optionalOfAuthor;
        }

        return Optional.empty();
    }
}