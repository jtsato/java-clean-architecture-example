package io.github.jtsato.bookstore.infra.author.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import io.github.jtsato.bookstore.infra.author.domain.AuthorEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface AuthorRepository extends MongoRepository<AuthorEntity, String>, QuerydslPredicateExecutor<AuthorEntity> {

	Optional<AuthorEntity> findByAuthorId(final Long authorId);

	Optional<AuthorEntity> findByName(final String name);
	
	Optional<AuthorEntity> findByBooksBookId(final Long bookId);

}
