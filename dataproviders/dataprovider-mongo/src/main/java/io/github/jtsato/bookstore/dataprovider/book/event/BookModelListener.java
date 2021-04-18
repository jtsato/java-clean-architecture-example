package io.github.jtsato.bookstore.dataprovider.book.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import io.github.jtsato.bookstore.dataprovider.common.SequenceGeneratorService;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class BookModelListener extends AbstractMongoEventListener<BookEntity> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public BookModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<BookEntity> event) {
        if (event.getSource().getBookId() == null) {
            event.getSource().setBookId(sequenceGenerator.generateSequence(BookEntity.SEQUENCE_NAME));
        }
    }
}
