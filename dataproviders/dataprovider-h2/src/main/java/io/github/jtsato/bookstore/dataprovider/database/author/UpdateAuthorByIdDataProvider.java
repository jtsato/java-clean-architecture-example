package io.github.jtsato.bookstore.dataprovider.database.author;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.UpdateAuthorByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional
@Service
public class UpdateAuthorByIdDataProvider implements UpdateAuthorByIdGateway {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Optional<Author> updateAuthorById(final Author author) {

        final Optional<AuthorEntity> optional = authorRepository.findById(author.getId());

        if (!optional.isPresent()) {
            return Optional.empty();
        }
        
        final AuthorEntity authorEntity = optional.get();
        
        return Optional.of(AuthorMapper.of(authorRepository.saveAndFlush(updateAuthorEntity(authorEntity, author))));
    }
    
    private AuthorEntity updateAuthorEntity(final AuthorEntity authorEntity, final Author author) {
        authorEntity.setName(author.getName());
        authorEntity.setBirthday(author.getBirthday());
        authorEntity.setGender(author.getGender().name());
        return authorEntity;
    }
}
