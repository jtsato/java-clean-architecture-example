package io.github.jtsato.bookstore.dataprovider.author.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface AuthorRepository extends EntityGraphJpaRepository<AuthorEntity, Long>, EntityGraphQuerydslPredicateExecutor<AuthorEntity> {

    Optional<AuthorEntity> findByName(final String name);
}
