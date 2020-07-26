package io.github.jtsato.bookstore.entrypoint.rest.book.domain.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class RegisterBookResponse implements Serializable {

    private static final long serialVersionUID = 8114419514719050282L;

    private Long id;

    private String title;
    
    private LocalDateTime creationDate;
    
    private BigDecimal price;

    private RegisterBookAuthorResponse author;
}
