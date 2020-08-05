package io.github.jtsato.bookstore.core.author.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.SearchAuthorsGateway;
import io.github.jtsato.bookstore.core.author.usecase.impl.SearchAuthorsUseCaseImpl;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato  
 */

class SearchAuthorsUseCaseTest {

    @Mock
    private final SearchAuthorsGateway searchAuthorsGateway = Mockito.mock(SearchAuthorsGateway.class);

    @InjectMocks
    private final SearchAuthorsUseCase searchAuthorsUseCase = new SearchAuthorsUseCaseImpl(searchAuthorsGateway);


    @DisplayName("Fail to search authors with inconsistent parameters")
    @Test
    void failToSearchAuthorsWithInconsistentParameters() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            new SearchAuthorsParameters(null, StringUtils.SPACE, StringUtils.SPACE, "2019-02-29", "2019-02-29");
        });

        assertThat(exception).isInstanceOf(ConstraintViolationException.class);
        final ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
        assertThat(constraintViolationException.getMessage()).contains("startBirthdayDate: validation.author.start.birthday.date.notvalid");
        assertThat(constraintViolationException.getMessage()).contains("endBirthdayDate: validation.author.end.birthday.date.notvalid");
    }

    @DisplayName("Successful to search authors if found")
    @Test
    void successfulToSearchAuthorsIfFound() {

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, null, null, null, null);
        final Page<Author> pageWithOneAuthor = mockPageWithOneAuthor();

        when(searchAuthorsGateway.searchAuthors(parameters, 0, 1, "id:asc")).thenReturn(pageWithOneAuthor);

        final Page<Author> pageOfAuthors = searchAuthorsUseCase.searchAuthors(parameters, 0, 1, "id:asc");

        assertThat(pageOfAuthors).isNotNull();

        final Pageable pageable = pageOfAuthors.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isOne();
        assertThat(pageable.getTotalOfElements()).isOne();
        assertThat(pageable.getTotalPages()).isOne();

        final List<Author> authors = pageOfAuthors.getContent();

        assertThat(authors).isNotNull().isNotEmpty();
        assertThat(authors.size()).isOne();

        final Author author = authors.get(0);

        assertThat(author.getId()).isEqualTo(1L);
        assertThat(author.getName()).isEqualTo("Joshua Bloch");
        assertThat(author.getGender()).isEqualTo(Gender.MALE);
        assertThat(author.getBirthday()).isEqualTo(LocalDate.parse("1961-08-28"));
    }

    private Page<Author> mockPageWithOneAuthor() {

        final List<Author> content = new ArrayList<>(1);
        content.add(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));

        return new PageImpl<Author>(content, new Pageable(0, 1, 1, 1L, 1));
    }

    @DisplayName("Fail to get author by id if not found")
    @Test
    void successfulToSearchAuthorsIfNotFound() {

        final SearchAuthorsParameters parameters = new SearchAuthorsParameters(null, null, null, null, null);
        final Page<Author> emptyAuthorsPage = mockEmptyAuthorsPage();
        when(searchAuthorsGateway.searchAuthors(parameters, 0, 1, "id:asc")).thenReturn(emptyAuthorsPage);

        final Page<Author> pageOfAuthors = searchAuthorsUseCase.searchAuthors(parameters, 0, 1, "id:asc");

        assertThat(pageOfAuthors).isNotNull();

        final List<Author> authors = pageOfAuthors.getContent();

        assertThat(authors).isNotNull().isEmpty();

        assertThat(pageOfAuthors.getPageable()).isNotNull();

        final Pageable pageable = pageOfAuthors.getPageable();

        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();
    }

    private Page<Author> mockEmptyAuthorsPage() {
        return new PageImpl<Author>(Collections.emptyList(), new Pageable(0, 1, 0, 0L, 0));
    }
}
