package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class SearchBooksRequest implements Serializable {
    
    private static final long serialVersionUID = 7019480436873286085L;

    @Default
    private SearchBooksAuthorRequest author = new SearchBooksAuthorRequest();
    
    private String title;
    
    private String startCreationDate;
    
    private String endCreationDate;
}
