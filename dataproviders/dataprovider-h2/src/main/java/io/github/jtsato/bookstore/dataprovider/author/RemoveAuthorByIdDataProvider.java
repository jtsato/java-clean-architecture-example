package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RemoveAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

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
        return optional.map(this::removeAuthorEntity);
    }

    private Author removeAuthorEntity(final AuthorEntity authorEntity) {
        final Author author = AuthorMapper.of(authorEntity);
        authorRepository.delete(authorEntity);
        return author;
    }
}
