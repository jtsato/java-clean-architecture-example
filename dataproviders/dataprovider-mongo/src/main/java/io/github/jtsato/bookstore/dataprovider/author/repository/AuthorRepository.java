package io.github.jtsato.bookstore.dataprovider.author.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface AuthorRepository extends MongoRepository<AuthorEntity, Long>, QuerydslPredicateExecutor<AuthorEntity> {

    Optional<AuthorEntity> findByName(final String name);
}
