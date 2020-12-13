package io.github.jtsato.bookstore.dataprovider.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;

	private long seq;
}
