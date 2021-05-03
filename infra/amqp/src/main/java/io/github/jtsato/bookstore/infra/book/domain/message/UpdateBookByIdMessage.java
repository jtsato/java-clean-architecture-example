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
public class UpdateBookByIdMessage implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;

    private Long id;
    private Long authorId;
    private String title;
    private BigDecimal price;
    private Boolean available;
}
