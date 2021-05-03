package io.github.jtsato.bookstore.infra.book.domain.message;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterBookMessage implements Serializable {

    private static final long serialVersionUID = -1401793357376446399L;

    private Long authorId;
    private String title;
    private BigDecimal price;
    private Boolean available;
}
