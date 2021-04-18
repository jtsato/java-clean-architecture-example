package io.github.jtsato.bookstore.dataprovider.book.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.author.mapper.AuthorMapper;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Mapper(uses = AuthorMapper.class)
public interface BookMapper {

    @Mapping(source = "bookId", target = "id")
    @Mapping(target = "author", ignore = true)
    Book of(final BookEntity bookEntity);

    @Mapping(source = "bookEntity.bookId", target = "id")
    @Mapping(source = "authorEntity", target = "author")
    Book of(final BookEntity bookEntity, final AuthorEntity authorEntity);

	@Mapping(source = "id", target = "bookId")	
    BookEntity of(final Book book);
}
