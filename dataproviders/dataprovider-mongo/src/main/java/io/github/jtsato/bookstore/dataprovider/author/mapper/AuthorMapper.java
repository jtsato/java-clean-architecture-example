package io.github.jtsato.bookstore.dataprovider.author.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.book.mapper.BookMapper;

/**
 * @author Jorge Takeshi Sato
 */

@Mapper(uses = BookMapper.class)
public interface AuthorMapper {

	@Mapping(source = "authorId", target = "id")	
    Author of(final AuthorEntity authorEntity);
    
	@Mapping(source = "id", target = "authorId")	
	@Mapping(target = "objectId", ignore = true)
	@Mapping(target = "books", ignore = true)
    AuthorEntity of(final Author author);
}