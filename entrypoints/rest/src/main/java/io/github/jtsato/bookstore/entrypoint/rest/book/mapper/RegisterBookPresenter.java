package io.github.jtsato.bookstore.entrypoint.rest.book.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.RegisterBookAuthorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.RegisterBookResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterBookPresenter {

    public static RegisterBookResponse of(final Book book) {
        return new RegisterBookResponse(book.getId(), of(book.getAuthor()), book.getTitle(), book.getPrice(), book.getAvailable(), book.getCreationDate(), book.getUpdateDate());
    }

    public static RegisterBookAuthorResponse of(final Author author) {
        return new RegisterBookAuthorResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthday());
    }
}
