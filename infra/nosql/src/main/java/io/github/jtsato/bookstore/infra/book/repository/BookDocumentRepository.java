package io.github.jtsato.bookstore.infra.book.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import io.github.jtsato.bookstore.infra.book.domain.BookDocumentEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Repository
public interface BookDocumentRepository extends MongoRepository<BookDocumentEntity, Long>, QuerydslPredicateExecutor<BookDocumentEntity> {

    Optional<BookDocumentEntity> findByBookId(final Long bookId);
}
