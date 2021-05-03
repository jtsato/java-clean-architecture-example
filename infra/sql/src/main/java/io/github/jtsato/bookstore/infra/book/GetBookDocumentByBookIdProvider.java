package io.github.jtsato.bookstore.infra.book;

import java.util.Optional;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jtsato.bookstore.core.book.domain.BookDocument;
import io.github.jtsato.bookstore.core.book.gateway.GetBookDocumentByBookIdGateway;
import io.github.jtsato.bookstore.infra.book.domain.BookDocumentEntity;
import io.github.jtsato.bookstore.infra.book.mapper.BookDocumentMapper;
import io.github.jtsato.bookstore.infra.book.repository.BookDocumentRepository;

/**
 * @author Jorge Takeshi Sato
 */

@Transactional
@Service
public class GetBookDocumentByBookIdProvider implements GetBookDocumentByBookIdGateway {

    private final BookDocumentMapper bookDocumentMapper = Mappers.getMapper(BookDocumentMapper.class);

    @Autowired
    BookDocumentRepository bookDocumentRepository;

    @Override
    public Optional<BookDocument> execute(final Long bookId) {
        final Optional<BookDocumentEntity> optional = bookDocumentRepository.findByBookId(bookId);
        return optional.map(bookDocumentMapper::of);
    }
}
