package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@AllArgsConstructor
public final class SearchBooksRequest implements Serializable {
    
    private static final long serialVersionUID = 7019480436873286085L;

    private final SearchBooksAuthorRequest author = new SearchBooksAuthorRequest();
    
    private final String title;
    
    private final String startCreationDate;
    
    private final String endCreationDate;
}
