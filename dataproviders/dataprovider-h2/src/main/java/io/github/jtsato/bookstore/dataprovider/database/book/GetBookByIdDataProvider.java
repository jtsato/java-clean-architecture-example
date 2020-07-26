package io.github.jtsato.bookstore.dataprovider.database.book;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain.EntityGraphUtils;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.dataprovider.database.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.database.book.mapper.BookMapper;
import io.github.jtsato.bookstore.dataprovider.database.book.repository.BookRepository;

/**
 * @author Jorge Takeshi Sato  
 */

@Transactional(readOnly = true)
@Service
public class GetBookByIdDataProvider implements GetBookByIdGateway {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Optional<Book> getBookById(final Long id) {

        final Optional<BookEntity> optional = bookRepository.findById(id, EntityGraphUtils.fromAttributePaths("author"));

        if (optional.isPresent()) {
            return Optional.of(BookMapper.of(optional.get()));
        }

        return Optional.empty();
    }
}
