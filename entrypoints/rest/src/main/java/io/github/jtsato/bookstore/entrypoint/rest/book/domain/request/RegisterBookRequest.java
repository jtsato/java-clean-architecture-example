package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookRequest implements Serializable {

    private static final long serialVersionUID = -2642365523322205913L;

    private Long authorId;

    private String title;

    private BigDecimal price;

    private Boolean available;
}
