package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class SaveBookDocumentRequest implements Serializable {

    private static final long serialVersionUID = -2642365523322205913L;

	private final Long bookId;
	
	private final String contentType;
	
	private final String extension;
	
	private final String name;
	
	private final Long size;

	@JsonIgnore
    private final String content;
}