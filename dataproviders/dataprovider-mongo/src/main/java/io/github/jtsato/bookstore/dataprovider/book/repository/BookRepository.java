package io.github.jtsato.bookstore.dataprovider.book.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;

/**
 * @book Jorge Takeshi Sato
 */

@Repository
public interface BookRepository extends MongoRepository<AuthorEntity, String>, QuerydslPredicateExecutor<BookEntity> {

	Optional<BookEntity> findByBooksBookId(final Long bookId);

	Optional<BookEntity> findByBooksTitleIgnoreCase(final String title);

    Page<BookEntity> findByAuthorId(final Long authorId, final PageRequest pageRequest);

}
