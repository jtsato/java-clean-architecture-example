package io.github.jtsato.bookstore.core.book.usecase.parameter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    
    public RegisterBookParameters(final String title, final Long authorId) {
        this.title = title;
        this.authorId = authorId;
        this.validateSelf();
    }
}
