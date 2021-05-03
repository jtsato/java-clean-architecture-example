package io.github.jtsato.bookstore.infra.book.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.github.jtsato.bookstore.infra.book.domain.BookEntity;
import io.github.jtsato.bookstore.infra.common.SequenceGeneratorService;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class BookModelListener extends AbstractMongoEventListener<BookEntity> {

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BookEntity> event) {
        if (event.getSource().getBookId() == null) {
            event.getSource().setBookId(sequenceGenerator.generateSequence(BookEntity.SEQUENCE_NAME));
        }
    }
}
