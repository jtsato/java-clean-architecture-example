package io.github.jtsato.bookstore.dataprovider.book.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface BookRepository extends EntityGraphJpaRepository<BookEntity, Long>, EntityGraphQuerydslPredicateExecutor<BookEntity> {

    Optional<BookEntity> findByTitleIgnoreCase(final String title);
}
