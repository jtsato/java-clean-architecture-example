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

	public static SaveBookDocumentResponse of(final BookDocument bookContent) {
		return new SaveBookDocumentResponse(bookContent.getId(), bookContent.getBookId(), bookContent.getCreationDate(), bookContent.getUpdateDate());
	}
}
