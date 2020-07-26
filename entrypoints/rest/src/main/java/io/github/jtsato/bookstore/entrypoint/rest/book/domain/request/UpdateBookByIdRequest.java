package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class UpdateBookByIdRequest implements Serializable {

    private static final long serialVersionUID = -2642365523322205913L;
    
    private String title;
    
    private Long authorId;
}