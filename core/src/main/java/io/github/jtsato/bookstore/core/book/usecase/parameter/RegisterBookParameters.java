package io.github.jtsato.bookstore.core.book.usecase.parameter;

import java.io.Serializable;
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
public class RegisterBookParameters extends SelfValidating<RegisterBookParameters> implements Serializable {

    private static final long serialVersionUID = 9095146175697454252L;

    @NotNull(message = "validation.author.id.null")
    private final Long authorId;

    @NotBlank(message = "validation.book.title.blank")
    private final String title;

    @NotNull(message = "validation.book.available.null")
    private final Boolean available;

    @NotNull(message = "validation.book.price.null")
    @PositiveOrZero(message = "validation.book.price.negative")
    private final BigDecimal price;

    public RegisterBookParameters(final Long authorId, final String title, final BigDecimal price, final Boolean available) {
        this.authorId = authorId;
        this.title = title;
        this.price = price;
        this.available = available;
        this.validateSelf();
    }
}
