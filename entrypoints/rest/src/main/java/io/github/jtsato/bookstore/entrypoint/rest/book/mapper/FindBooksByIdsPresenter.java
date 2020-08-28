package io.github.jtsato.bookstore.entrypoint.rest.book.mapper;

import java.util.ArrayList;
import java.util.List;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.FindBooksByIdsAuthorResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.FindBooksByIdsInnerResponse;
import io.github.jtsato.bookstore.entrypoint.rest.book.domain.response.FindBooksByIdsResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FindBooksByIdsPresenter {

    public static FindBooksByIdsResponse of(final Page<Book> page) {

        final List<Book> books = page.getContent();

        final List<FindBooksByIdsInnerResponse> content = new ArrayList<>(books.size());
        books.forEach(element -> content.add(of(element)));

        return new FindBooksByIdsResponse(content, page.getPageable());
    }

    private static FindBooksByIdsInnerResponse of(final Book book) {
        return new FindBooksByIdsInnerResponse(book.getId(),
                                               of(book.getAuthor()),
                                               book.getTitle(),
                                               book.getPrice(),
                                               book.getAvailable(),
                                               book.getCreationDate(),
                                               book.getUpdateDate());
    }

    private static FindBooksByIdsAuthorResponse of(final Author author) {
        return new FindBooksByIdsAuthorResponse(author.getId(), author.getName(), author.getGender().toString(), author.getBirthdate());
    }
}
