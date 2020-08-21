package io.github.jtsato.bookstore.core.book.usecase.parameter;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.tuple.ImmutablePair;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.validation.LocalDateTimeConstraint;
import io.github.jtsato.bookstore.core.common.validation.SelfValidating;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ToString
public class SearchBooksParameters extends SelfValidating<SearchBooksParameters>  implements Serializable {

    private static final long serialVersionUID = 8184330828206770082L;

    private SearchAuthorsParameters authorParameters;

    private String title;

    private BigDecimal startPrice;

    private BigDecimal endPrice;

    private Boolean available;

    @LocalDateTimeConstraint(message = "validation.book.start.creation.date.invalid")
    private String startCreationDate;

    @LocalDateTimeConstraint(message = "validation.book.end.creation.date.invalid")
    private String endCreationDate;

    @LocalDateTimeConstraint(message = "validation.book.start.update.date.invalid")
    private String startUpdateDate;

    @LocalDateTimeConstraint(message = "validation.book.end.creation.date.invalid")
    private String endUpdateDate;

    public SearchBooksParameters(final SearchAuthorsParameters authorParameters,
                                 final String title,
                                 final ImmutablePair<BigDecimal, BigDecimal> priceRange,
                                 final Boolean available,
                                 final ImmutablePair<String, String> creationDateRange,
                                 final ImmutablePair<String, String> updateDateRange) {
        this.authorParameters = authorParameters;
        this.title = title;
        this.startPrice = priceRange.getLeft();
        this.endPrice = priceRange.getRight();
        this.available = available;
        this.startCreationDate = creationDateRange.getLeft();
        this.endCreationDate = creationDateRange.getRight();
        this.startUpdateDate = updateDateRange.getLeft();
        this.endUpdateDate = updateDateRange.getRight();
        this.validateSelf();
    }
}
