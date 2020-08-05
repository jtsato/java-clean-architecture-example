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
public class SearchBooksInnerResponse implements Serializable {

    private static final long serialVersionUID = 8114419514719050282L;

    private final Long id;

    private final SearchBooksAuthorResponse author;

    private final String title;

    private final BigDecimal price;

    private final Boolean available;

    private final LocalDateTime creationDate;

    private final LocalDateTime updateDate;
}
