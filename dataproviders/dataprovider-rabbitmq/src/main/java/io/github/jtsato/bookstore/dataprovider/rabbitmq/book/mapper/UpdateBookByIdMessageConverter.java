package io.github.jtsato.bookstore.dataprovider.rabbitmq.book.mapper;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message.UpdateBookByIdMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateBookByIdMessageConverter {

	public static UpdateBookByIdMessage of(final Book book) {
		return new UpdateBookByIdMessage(
				book.getId(), 
				book.getAuthor().getId(), 
				book.getTitle(), 
				book.getPrice(),
				book.getAvailable());
	}
}
