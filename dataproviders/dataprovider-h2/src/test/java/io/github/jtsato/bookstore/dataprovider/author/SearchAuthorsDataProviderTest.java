package io.github.jtsato.bookstore.dataprovider.author;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({SearchAuthorsDataProvider.class})
@Sql("SearchAuthorsDataProviderTest.sql")
class SearchAuthorsDataProviderTest {

    @Autowired
    private SearchAuthorsDataProvider searchAuthorsDataProvider;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("Successful to search authors if found")
    @Test
    void successfulToSearchAuthorsIfFound() {

        final String name = "Robert Cecil Martin";
        final String gender = Gender.MALE.name();
        final String startBirthdayDate = "1952-12-04";
        final String endBirthdayDate = "1952-12-06";

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, name, gender, startBirthdayDate, endBirthdayDate);

        final String orderBy = "name:desc,gender:asc";

        final Page<Author> pageOfAuthors = searchAuthorsDataProvider.searchAuthors(parameters, null, null, orderBy);

        assertThat(pageOfAuthors).isNotNull();

        final List<Author> authors = pageOfAuthors.getContent();

        assertThat(authors).isNotNull().isNotEmpty();

        final Pageable pageable = pageOfAuthors.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        assertThat(authorRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to search authors if not found")
    @Test
    void successfulToSearchAuthorsIfNotFound() {

        final String name = "Joshua Bloch";
        final String gender = Gender.MALE.name();
        final String startBirthdayDate = "1952-12-06";
        final String endBirthdayDate = "1952-12-06";

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, name, gender, startBirthdayDate, endBirthdayDate);

        final String orderBy = null;

        final Page<Author> pageOfAuthors = searchAuthorsDataProvider.searchAuthors(parameters, 0, -1, orderBy);

        assertThat(pageOfAuthors).isNotNull();

        final List<Author> authors = pageOfAuthors.getContent();

        assertThat(authors).isNotNull().isEmpty();

        final Pageable pageable = pageOfAuthors.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isEqualTo(10);
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();

        assertThat(authorRepository.count()).isEqualTo(4);
    }

    @DisplayName("Successful to paging authors if found")
    @Test
    void successfulToPagingAuthorsIfFound() {

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, null, null, null, null);

        final String orderBy = "gender:asc,name:desc";

        final Page<Author> pageOfAuthors = searchAuthorsDataProvider.searchAuthors(parameters, 1, 3, orderBy);

        assertThat(pageOfAuthors).isNotNull();

        final List<Author> authors = pageOfAuthors.getContent();

        assertThat(authors).isNotNull().isNotEmpty();

        final Pageable pageable = pageOfAuthors.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isOne();
        assertThat(pageable.getSize()).isEqualTo(3);
        assertThat(pageable.getTotalOfElements()).isEqualTo(4);
        assertThat(pageable.getTotalPages()).isEqualTo(2);

        assertThat(authorRepository.count()).isEqualTo(4);
    }
}
