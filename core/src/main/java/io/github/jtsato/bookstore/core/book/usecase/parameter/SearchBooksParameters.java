package io.github.jtsato.bookstore.core.book.usecase.parameter;

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
public class SearchBooksParameters extends SelfValidating<SearchBooksParameters> {

    private SearchAuthorsParameters authorParameters;

    private String title;

    private BigDecimal startPrice;

    private BigDecimal endPrice;

    private Boolean available;

    @LocalDateTimeConstraint(message = "validation.book.start.creation.date.notvalid")
    private String startCreationDate;

    @LocalDateTimeConstraint(message = "validation.book.end.creation.date.notvalid")
    private String endCreationDate;

    @LocalDateTimeConstraint(message = "validation.book.start.update.date.notvalid")
    private String startUpdateDate;

    @LocalDateTimeConstraint(message = "validation.book.end.creation.date.notvalid")
    private String endUpdateDate;

    public SearchBooksParameters(final SearchAuthorsParameters authorParameters,
                                 final String title,
                                 final ImmutablePair<BigDecimal, BigDecimal> prices,
                                 final Boolean available,
                                 final ImmutablePair<String, String> creationDates,
                                 final ImmutablePair<String, String> updateDates) {
        this.authorParameters = authorParameters;
        this.title = title;
        this.startPrice = prices.getLeft();
        this.endPrice = prices.getRight();
        this.available = available;
        this.startCreationDate = creationDates.getLeft();
        this.endCreationDate = creationDates.getRight();
        this.startUpdateDate = updateDates.getLeft();
        this.endUpdateDate = updateDates.getRight();
        this.validateSelf();
    }
}
