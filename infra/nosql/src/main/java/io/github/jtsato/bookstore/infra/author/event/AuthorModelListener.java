package io.github.jtsato.bookstore.infra.author.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import io.github.jtsato.bookstore.infra.author.domain.AuthorEntity;
import io.github.jtsato.bookstore.infra.common.SequenceGeneratorService;

/**
 * @author Jorge Takeshi Sato
 */

@Component
public class AuthorModelListener extends AbstractMongoEventListener<AuthorEntity> {

    @Autowired
	private SequenceGeneratorService sequenceGenerator;

	@Override
	public void onBeforeConvert(BeforeConvertEvent<AuthorEntity> event) {
		if (event.getSource().getAuthorId() == null) {
			event.getSource().setAuthorId(sequenceGenerator.generateSequence(AuthorEntity.SEQUENCE_NAME));
		}
	}
}
