package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@AllArgsConstructor
public final class FindBooksByIdsRequest implements Serializable {
    
	private static final long serialVersionUID = 5716883659137908789L;
	
	private final List<Long> ids;
}
