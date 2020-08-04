package io.github.jtsato.bookstore.dataprovider.rabbitmq.book.mapper;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message.SaveBookDocumentMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveBookDocumentMessageConverter {

	public static SaveBookDocumentMessage of(final BookDocument bookDocument) {
		return new SaveBookDocumentMessage(bookDocument.getId(), bookDocument.getBookId(),
				bookDocument.getContentType(), bookDocument.getExtension(), bookDocument.getName(),
				bookDocument.getSize(), bookDocument.getContent());
	}
}
