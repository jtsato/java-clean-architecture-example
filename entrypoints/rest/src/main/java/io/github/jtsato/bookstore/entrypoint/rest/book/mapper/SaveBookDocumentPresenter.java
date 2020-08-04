package io.github.jtsato.bookstore.entrypoint.rest.book.mapper;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.SaveBookDocumentResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveBookDocumentPresenter {

	public static SaveBookDocumentResponse of(final BookDocument bookDocument) {
		return new SaveBookDocumentResponse(bookDocument.getId(), 
											bookDocument.getBookId(),
											bookDocument.getContentType(), 
											bookDocument.getExtension(), 
											bookDocument.getName(),
											bookDocument.getSize(), 
											bookDocument.getCreationDate(), 
											bookDocument.getUpdateDate());
	}
}
