package io.github.jtsato.bookstore.core.book.usecase.parameter;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

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
public class UpdateBookByIdParameters extends SelfValidating<UpdateBookByIdParameters> {

    @NotNull(message = "validation.book.id.null")
    private final Long id;

    @NotNull(message = "validation.author.id.null")
    private final Long authorId;

    @NotBlank(message = "validation.book.title.blank")
    private final String title;

    @NotNull(message = "validation.book.price.null")
    @PositiveOrZero(message = "validation.book.price.negative")
    private final BigDecimal price;

    @NotNull(message = "validation.book.available.null")
    private final Boolean available;

    public UpdateBookByIdParameters(final Long id, final Long authorId, final String title, final BigDecimal price, final Boolean available) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.price = price;
        this.available = available;
        this.validateSelf();
    }
}
