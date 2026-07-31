package io.github.jtsato.bookstore.infra.author;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.GetAuthorByNameGateway;
import io.github.jtsato.bookstore.infra.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.infra.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.infra.author.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetAuthorByNameProvider implements GetAuthorByNameGateway {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);

    private final AuthorRepository authorRepository;

    @Override
    public Optional<Author> execute(final String name) {
        final Optional<AuthorEntity> optional = authorRepository.findByName(name);
        return optional.map(authorMapper::of);
    }
}
