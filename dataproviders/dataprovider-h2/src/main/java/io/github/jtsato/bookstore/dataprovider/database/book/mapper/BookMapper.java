package io.github.jtsato.bookstore.dataprovider.database.book.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookMapper {

    public static Book of(final BookEntity bookEntity) {
        final Author author = AuthorMapper.of(bookEntity.getAuthor());
		return new Book(bookEntity.getId(), author, bookEntity.getTitle(), bookEntity.getPrice(), bookEntity.getAvailable(), bookEntity.getCreationDate(), bookEntity.getUpdateDate());
    }

    public static BookEntity of(final Book book) {
        final AuthorEntity authorEntity = AuthorMapper.of(book.getAuthor());
		return new BookEntity(book.getId(), authorEntity, book.getTitle(), book.getPrice(), book.getAvailable(), book.getCreationDate(), book.getUpdateDate());
    }
}
