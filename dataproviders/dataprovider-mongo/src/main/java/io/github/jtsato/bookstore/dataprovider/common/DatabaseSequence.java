package io.github.jtsato.bookstore.dataprovider.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@QueryEntity
@Document(collection = "database_sequences")
public class DatabaseSequence {

	@Id
	private String id;

	private long seq;
}
