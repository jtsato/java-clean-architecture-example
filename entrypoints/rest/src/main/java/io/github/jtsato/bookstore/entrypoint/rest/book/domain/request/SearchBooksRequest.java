package io.github.jtsato.bookstore.entrypoint.rest.book.domain.request;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public final class SearchBooksRequest implements Serializable {

    private static final long serialVersionUID = 7019480436873286085L;

    private SearchBooksAuthorRequest author = new SearchBooksAuthorRequest();
    private String title;
    private Boolean available;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
    private String startCreationDate;
    private String endCreationDate;
    private String startUpdateDate;
    private String endUpdateDate;
}
