package io.github.jtsato.bookstore.core.author.usecase;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.author.usecase.impl.FindAuthorsByIdsUseCaseImpl;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;

/**
 * @author Jorge Takeshi Sato  
 */

class FindAuthorsByIdsUseCaseTest {

    @Mock
    private FindAuthorsByIdsGateway findAuthorsByIdsGateway = Mockito.mock(FindAuthorsByIdsGateway.class);

    @InjectMocks
    private FindAuthorsByIdsUseCase findAuthorsByIdsUseCase = new FindAuthorsByIdsUseCaseImpl(findAuthorsByIdsGateway);

    @DisplayName("Fail to find authors by IDs if parameters are not valid")
    @Test
    void failToFindAuthorsByIdsIfParametersAreNotValid() {
        
        when(findAuthorsByIdsGateway.findAuthorsByIds(null)).thenReturn(null);

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            findAuthorsByIdsUseCase.findAuthorsByIds(null);
        });
        
        assertThat(exception).isInstanceOf(InvalidParameterException.class);
        
        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.authors.ids.null");
    }    
    
    @DisplayName("Successful to find authors by IDs, when at least one is found")
    @Test
    void successfulToFindAuthorsByIdsIfFound() {

    	final List<Long> ids = Arrays.asList(new Long[] {1L, 2L});
    	
        when(findAuthorsByIdsGateway.findAuthorsByIds(ids)).thenReturn(mockFindAuthorsByIdsGatewayReturn());
        
        final Page<Author> pageOfAuthors = findAuthorsByIdsUseCase.findAuthorsByIds(ids);
        
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

    private Page<Author> mockFindAuthorsByIdsGatewayReturn() {
    	
        final List<Author> content = new ArrayList<>(1);
        content.add(new Author(1L, "Joshua Bloch", Gender.MALE, LocalDate.parse("1961-08-28")));

        return new PageImpl<Author>(content, new Pageable(0, 1, 1, 1L, 1));
    }

    @DisplayName("Fail to find authors by IDs if not found")
    @Test
    void failToFindAuthorsByIdsIfNotFound() {
    	
    	final List<Long> ids = Arrays.asList(new Long[] {1L, 2L});    	

    	final Page<Author> pageOfAuthors = new PageImpl<Author>(Collections.emptyList(), new Pageable(0, 1, 0, 0L, 0));
        
		when(findAuthorsByIdsGateway.findAuthorsByIds(ids)).thenReturn(pageOfAuthors);

        final Pageable pageable = pageOfAuthors.getPageable();
        
        assertThat(pageable).isNotNull();
        assertThat(pageable.getPage()).isZero();
        assertThat(pageable.getSize()).isOne();
        assertThat(pageable.getNumberOfElements()).isZero();
        assertThat(pageable.getTotalOfElements()).isZero();
        assertThat(pageable.getTotalPages()).isZero();
    }
    
    @DisplayName("Fail to find authors by IDs if the limit is exceeded")
    @Test
    void failToFindAuthorsByIdsIfTheLimitIsExceeded() {
    	
    	final List<Long> ids = new ArrayList<Long>(1001);
    	
    	for (int index = 1; index <= 1001; index++) {
			ids.add((long) index);
		}
    	
        when(findAuthorsByIdsGateway.findAuthorsByIds(ids)).thenReturn(mockFindAuthorsByIdsGatewayReturn());
        
        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            findAuthorsByIdsUseCase.findAuthorsByIds(ids);
        });
        
        assertThat(exception).isInstanceOf(InvalidParameterException.class);
        
        final InvalidParameterException invalidParameterException = (InvalidParameterException) exception;
        assertThat(invalidParameterException.getMessage()).isEqualTo("validation.get.by.ids.limit");        
    }
    
    @DisplayName("Successful to find authors by IDs if the limit is exceeded")
    @Test
    void  successfulToFindAuthorsByIdsIfTheLimitIsNotExceeded() {
    	
    	final List<Long> ids = new ArrayList<Long>(1000);
    	
    	for (int index = 1; index <= 1000; index++) {
			ids.add((long) index);
		}
    	
        when(findAuthorsByIdsGateway.findAuthorsByIds(ids)).thenReturn(mockFindAuthorsByIdsGatewayReturn());

        final Page<Author> pageOfAuthors = findAuthorsByIdsUseCase.findAuthorsByIds(ids);
        
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
}
