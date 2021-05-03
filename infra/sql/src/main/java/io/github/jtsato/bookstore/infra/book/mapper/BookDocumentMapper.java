package io.github.jtsato.bookstore.infra.book.mapper;

import org.mapstruct.Mapper;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.infra.book.domain.BookDocumentEntity;

/**
 * @author Jorge Takeshi Sato
 */

@Mapper
public interface BookDocumentMapper {

    BookDocument of(final BookDocumentEntity bookDocumentEntity);

    BookDocumentEntity of(final BookDocument bookDocument);
}
