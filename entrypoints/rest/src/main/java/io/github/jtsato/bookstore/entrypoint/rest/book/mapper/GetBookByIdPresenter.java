package io.github.jtsato.bookstore.entrypoint.rest.book.mapper;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.GetBookByIdAuthorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.GetBookByIdResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetBookByIdPresenter {

    public static GetBookByIdResponse of(final Book book) {
        return new GetBookByIdResponse(book.getId(),
                                       of(book.getAuthor()),
                                       book.getTitle(),
                                       book.getPrice(),
                                       book.getAvailable(),
                                       book.getCreationDate(),
                                       book.getUpdateDate());
    }

    private static GetBookByIdAuthorResponse of(final Author author) {
        return new GetBookByIdAuthorResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
