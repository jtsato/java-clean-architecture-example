package io.github.jtsato.bookstore.dataprovider.database.book.repository;

import org.springframework.stereotype.Repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphQuerydslPredicateExecutor;

import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookDocumentEntity;

/**
 * @author Jorge Takeshi Sato  
 */

@Repository
public interface BookDocumentRepository extends EntityGraphJpaRepository<BookDocumentEntity, Long>, EntityGraphQuerydslPredicateExecutor<BookDocumentEntity> {

}
