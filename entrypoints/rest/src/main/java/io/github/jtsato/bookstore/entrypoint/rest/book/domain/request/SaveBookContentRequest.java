package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class SaveBookContentRequest implements Serializable {

	private static final long serialVersionUID = 263785374758987225L;

	private Long bookId;
    
    private String content;
}
