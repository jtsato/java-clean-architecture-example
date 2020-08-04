package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveBookDocumentRequest implements Serializable {

    private static final long serialVersionUID = -2642365523322205913L;

	private Long bookId;
	
	private String contentType;
	
	private String extension;
	
	private String name;
	
	private Long size;

	@JsonIgnore
    private String content;
}