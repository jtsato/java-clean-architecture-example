package io.github.jtsato.bookstore.core.book.usecase.parameter;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.github.jtsato.bookstore.core.common.validation.SelfValidating;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter 
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE) 
@EqualsAndHashCode(callSuper = false)
public class RegisterBookParameters extends SelfValidating<RegisterBookParameters> {

    @NotBlank(message = "validation.book.title.blank")
    private final String title;
    
    @NotNull(message = "validation.author.id.null")
    private final Long authorId;
    
    @NotNull(message = "validation.book.price.null")    
    @PositiveOrZero(message = "validation.book.price.negative")
    private final BigDecimal price;
    
    public RegisterBookParameters(final String title, final Long authorId, final BigDecimal price) {
        this.title = title;
        this.price = price;
        this.authorId = authorId;
        this.validateSelf();
    }
}
