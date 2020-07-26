package io.github.jtsato.bookstore.dataprovider.database.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({SearchBooksDataProvider.class})
@Sql("SearchBooksDataProviderTest.sql")
class SearchBooksDataProviderTest {

    @Autowired
    private SearchBooksDataProvider searchBooksDataProvider;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("Successful to search books if found")
    @Test
    void successfulToSearchBooksIfFound() {

        final String title = "Core Java SE 9 for the Impatient, 2nd Edition";
        final Long authorId = 1L;
        final String startCreationDate = "2020-03-12T21:04:59.000";
        final String endCreationDate = "2020-03-12T21:05:00.000";

        final SearchAuthorsParameters authorParameters = new SearchAuthorsParameters(authorId, null, null, null, null);
        final SearchBooksParameters parameters = new SearchBooksParameters(title, authorParameters, startCreationDate, endCreationDate);

        final String orderBy = "title:desc";

        final Page<Book> pageOfBooks = searchBooksDataProvider.searchBooks(parameters, null, null, orderBy);

        assertThat(pageOfBooks).isNotNull();

        final List<Book> Books = pageOfBooks.getContent();

        assertThat(Books).isNotNull().isNotEmpty();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to search books if not found")
    @Test
    void successfulToSearchBooksIfNotFound() {

        final String title = "Core Java SE 9 for the Impatient, 2nd Edition";
        final Long authorId = 1L;
        final String startCreationDate = "2020-03-12T21:05:00";
        final String endCreationDate = "2020-03-12T21:04:58";

        final SearchAuthorsParameters authorParameters = new SearchAuthorsParameters(authorId, null, null, null, null);
        final SearchBooksParameters parameters = new SearchBooksParameters(title, authorParameters, startCreationDate, endCreationDate);

        final String orderBy = "title:desc";

        final Page<Book> pageOfBooks = searchBooksDataProvider.searchBooks(parameters, 0, -1, orderBy);

        assertThat(pageOfBooks).isNotNull();

        final List<Book> Books = pageOfBooks.getContent();

        assertThat(Books).isNotNull().isEmpty();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();

        assertThat(bookRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to paging books if found")
    @Test
    void successfulToPagingBooksIfFound() {

        final SearchBooksParameters parameters = new SearchBooksParameters(null, null, null, null);

        final String orderBy = "creationDate,title:desc";

        final Page<Book> page = searchBooksDataProvider.searchBooks(parameters, 1, 3, orderBy);

        assertThat(page.getContent()).isNotEmpty();

        final Page<Book> pageOfBooks = searchBooksDataProvider.searchBooks(parameters, 0, 1, orderBy);

        assertThat(pageOfBooks).isNotNull();

        final List<Book> Books = pageOfBooks.getContent();

        assertThat(Books).isNotNull().isNotEmpty();

        final Pageable pageable = pageOfBooks.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isEqualTo(4);
        assertThat(pageable.getTotalPages()).isEqualTo(4);

        assertThat(bookRepository.count()).isEqualTo(4);
    }
}
