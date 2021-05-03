package io.github.jtsato.bookstore.infra.book.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import io.github.jtsato.bookstore.infra.book.domain.BookDocumentEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface BookDocumentRepository extends EntityGraphJpaRepository<BookDocumentEntity, Long>, EntityGraphQuerydslPredicateExecutor<BookDocumentEntity> {

    Optional<BookDocumentEntity> findByBookId(final Long bookId);
}
