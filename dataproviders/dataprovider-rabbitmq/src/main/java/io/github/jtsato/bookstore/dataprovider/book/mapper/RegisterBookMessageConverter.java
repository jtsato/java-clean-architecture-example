package io.github.jtsato.bookstore.dataprovider.book.mapper;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.book.domain.message.RegisterBookMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterBookMessageConverter {

    public static RegisterBookMessage of(final Book book) {
        return new RegisterBookMessage(book.getAuthor().getId(), book.getTitle(), book.getPrice(), book.getAvailable());
    }
}
