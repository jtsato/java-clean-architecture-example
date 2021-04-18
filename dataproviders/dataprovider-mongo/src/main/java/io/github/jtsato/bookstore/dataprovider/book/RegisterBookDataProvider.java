package io.github.jtsato.bookstore.dataprovider.book;

import java.util.Optional;
import java.util.function.Predicate;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.RegisterBookGateway;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
public class RegisterBookDataProvider implements RegisterBookGateway {

	private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

	@Autowired
	AuthorRepository authorRepository;

	@Override
	public Book execute(final Book book) {
		final AuthorEntity authorEntity = authorRepository.save(addBookToAuthor(book));
		final BookEntity bookEntity = findBookByTitle(authorEntity, book.getTitle());
		return bookMapper.of(bookEntity, authorEntity);
	}

	private AuthorEntity addBookToAuthor(final Book book) {
		final AuthorEntity authorEntity = getAuthorById(book.getAuthor().getId());
		final BookEntity bookEntity = bookMapper.of(book);
		authorEntity.getBooks().add(bookEntity);
		return authorEntity;
	}

	private AuthorEntity getAuthorById(final Long authorId) {
		final Optional<AuthorEntity> optional = authorRepository.findByAuthorId(authorId);
		return optional.orElseThrow(this::newDataIntegrityViolationException);
	}

	private DataIntegrityViolationException newDataIntegrityViolationException() {
		return new DataIntegrityViolationException("An unexpected error has occurred, please try again later!");
	}

	private BookEntity findBookByTitle(final AuthorEntity authorEntity, final String title) {
		final Predicate<? super BookEntity> byTitle = element -> element.getTitle().equals(title);
		return authorEntity.getBooks().stream().filter(byTitle).findFirst().orElseThrow(this::newDataIntegrityViolationException);
	}
}
