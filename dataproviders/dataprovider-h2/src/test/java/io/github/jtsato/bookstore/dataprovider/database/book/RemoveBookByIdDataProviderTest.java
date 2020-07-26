package io.github.jtsato.bookstore.dataprovider.database.book;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@DataJpaTest
@Import({RemoveBookByIdDataProvider.class})
@Sql("RemoveBookByIdDataProviderTest.sql")
class RemoveBookByIdDataProviderTest {
    
    @Autowired
    private RemoveBookByIdDataProvider removeBookByIdDataProvider;
    
    @Autowired
    private BookRepository bookRepository;
    
    @DisplayName("Successful to remove author by id if found")
    @Test
    void successfulToRemoveBookByIdIfFound() {
        
        final Optional<Book> optional = removeBookByIdDataProvider.removeBookById(4L);
        
        assertThat(optional).isPresent();
        assertThat(bookRepository.count()).isEqualTo(3);
    }
    
    @DisplayName("Fail to remove author by id if not found")
    @Test
    void failToRemoveBookByIdIfNotFound() {
        
        final Optional<Book> optional = removeBookByIdDataProvider.removeBookById(5L);
        
        assertThat(optional).isNotPresent();
        assertThat(bookRepository.count()).isEqualTo(4);
    }
}