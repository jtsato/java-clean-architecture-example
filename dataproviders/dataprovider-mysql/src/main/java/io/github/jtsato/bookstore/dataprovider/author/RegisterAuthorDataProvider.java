package io.github.jtsato.bookstore.dataprovider.author;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
public class RegisterAuthorDataProvider implements RegisterAuthorGateway {

    private final AuthorMapper authorMapper = Mappers.getMapper(AuthorMapper.class);
    
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author execute(final Author author) {
        final AuthorEntity authorEntity = authorMapper.of(author);
        return authorMapper.of(authorRepository.saveAndFlush(authorEntity));
    }
}
