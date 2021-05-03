package io.github.jtsato.bookstore.infra.book.mapper;

import org.mapstruct.Mapper;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.infra.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.infra.book.domain.BookEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Mapper(uses = AuthorMapper.class)
public interface BookMapper {

    Book of(final BookEntity bookEntity);

    BookEntity of(final Book book);
}
