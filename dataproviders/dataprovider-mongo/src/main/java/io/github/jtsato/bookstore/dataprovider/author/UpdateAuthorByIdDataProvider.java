package io.github.jtsato.bookstore.dataprovider.author;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
public class UpdateAuthorByIdDataProvider implements UpdateAuthorByIdGateway {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> execute(final Author author) {
        final Optional<AuthorEntity> optional = authorRepository.findByAuthorId(author.getId());
        return optional.map(authorEntity -> updateAuthorEntity(authorEntity, author));
    }

    private Author updateAuthorEntity(final AuthorEntity authorEntity, final Author author) {
        authorEntity.setName(author.getName());
        authorEntity.setBirthdate(author.getBirthdate());
        authorEntity.setGender(author.getGender().name());
        return authorMapper.of(authorRepository.save(authorEntity));
    }
}
