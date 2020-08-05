package io.github.jtsato.bookstore.dataprovider.book.mapper;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.dataprovider.book.domain.BookDocumentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookDocumentMapper {

    public static BookDocument of(final BookDocumentEntity bookDocumentEntity) {
        return new BookDocument(bookDocumentEntity.getId(),
                                bookDocumentEntity.getBookId(),
                                bookDocumentEntity.getContentType(),
                                bookDocumentEntity.getExtension(),
                                bookDocumentEntity.getName(),
                                bookDocumentEntity.getSize(),
                                bookDocumentEntity.getContent(),
                                bookDocumentEntity.getCreationDate(),
                                bookDocumentEntity.getUpdateDate());
    }

    public static BookDocumentEntity of(final BookDocument bookDocument) {
        return new BookDocumentEntity(bookDocument.getId(),
                                      bookDocument.getBookId(),
                                      bookDocument.getContentType(),
                                      bookDocument.getExtension(),
                                      bookDocument.getName(),
                                      bookDocument.getSize(),
                                      bookDocument.getContent(),
                                      bookDocument.getCreationDate(),
                                      bookDocument.getUpdateDate());
    }
}
