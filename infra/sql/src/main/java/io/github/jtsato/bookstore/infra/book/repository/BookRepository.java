package io.github.jtsato.bookstore.infra.book.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import io.github.jtsato.bookstore.infra.book.domain.BookEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface BookRepository extends EntityGraphJpaRepository<BookEntity, Long>, EntityGraphQuerydslPredicateExecutor<BookEntity> {

    Optional<BookEntity> findByTitleIgnoreCase(final String title, final EntityGraph entityGraph);
    
    Page<BookEntity> findByAuthorId(final Long authorId, final PageRequest pageRequest);
}
