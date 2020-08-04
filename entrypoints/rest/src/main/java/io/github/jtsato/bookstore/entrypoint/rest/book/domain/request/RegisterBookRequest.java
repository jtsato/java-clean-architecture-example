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
public class RegisterBookRequest implements Serializable {

    private static final long serialVersionUID = -2642365523322205913L;

    private final Long authorId;
    
    private final String title;
    
    private final BigDecimal price;
    
    private final Boolean available;
}
