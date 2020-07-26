package io.github.jtsato.bookstore.dataprovider.database.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.RegisterAuthorGateway;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class RegisterAuthorDataProvider implements RegisterAuthorGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author registerAuthor(final Author author) {
        final AuthorEntity authorEntity = AuthorMapper.of(author);
        return AuthorMapper.of(authorRepository.saveAndFlush(authorEntity));
    }
}
