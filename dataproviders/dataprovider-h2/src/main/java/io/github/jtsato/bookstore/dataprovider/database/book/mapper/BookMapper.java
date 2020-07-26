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
        return new Book(bookEntity.getId(), bookEntity.getTitle(), bookEntity.getCreationDate(), bookEntity.getPrice(), author);
    }

    public static BookEntity of(final Book book) {
        final AuthorEntity authorEntity = AuthorMapper.of(book.getAuthor());
        return new BookEntity(book.getId(), book.getTitle(), book.getCreationDate(), book.getPrice(), authorEntity);
    }
}
