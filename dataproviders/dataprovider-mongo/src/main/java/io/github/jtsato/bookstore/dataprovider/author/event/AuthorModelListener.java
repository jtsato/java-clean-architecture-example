package io.github.jtsato.bookstore.dataprovider.author.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.dataprovider.common.SequenceGeneratorService;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class AuthorModelListener extends AbstractMongoEventListener<AuthorEntity> {

	private SequenceGeneratorService sequenceGenerator;

	@Autowired
	public AuthorModelListener(SequenceGeneratorService sequenceGenerator) {
		this.sequenceGenerator = sequenceGenerator;
	}

	@Override
	public void onBeforeConvert(BeforeConvertEvent<AuthorEntity> event) {
		if (event.getSource().getId() < 1) {
			event.getSource().setId(sequenceGenerator.generateSequence(AuthorEntity.SEQUENCE_NAME));
		}
	}
}
