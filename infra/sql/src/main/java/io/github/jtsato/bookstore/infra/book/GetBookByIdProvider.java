package io.github.jtsato.bookstore.infra.book;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.domain2.DynamicEntityGraph;

import io.github.jtsato.bookstore.core.book.domain.Book;
import io.github.jtsato.bookstore.core.book.gateway.GetBookByIdGateway;
import io.github.jtsato.bookstore.infra.book.domain.BookEntity;
import io.github.jtsato.bookstore.infra.book.mapper.BookMapper;
import io.github.jtsato.bookstore.infra.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class GetBookByIdProvider implements GetBookByIdGateway {

    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);
    
    private final BookRepository bookRepository;

    @Override
    public Optional<Book> execute(final Long id) {
        final EntityGraph entityGraph = DynamicEntityGraph.loading(java.util.List.of("author"));
        final Optional<BookEntity> optional = bookRepository.findById(id, entityGraph);
        return optional.map(bookMapper::of);
    }
}
