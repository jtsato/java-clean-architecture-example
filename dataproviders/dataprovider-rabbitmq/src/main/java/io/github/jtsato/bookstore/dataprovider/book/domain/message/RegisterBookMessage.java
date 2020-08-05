package io.github.jtsato.bookstore.dataprovider.book.domain.message;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class RegisterBookMessage implements Serializable {

    private static final long serialVersionUID = -1401793357376446399L;

    private final Long authorId;

    private final String title;

    private final BigDecimal price;

    private final Boolean available;
}
