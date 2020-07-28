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
public class SaveBookContentParameters extends SelfValidating<SaveBookContentParameters> {

	@NotNull(message = "validation.book.id.null")
    private final Long bookId;
    
    @NotBlank(message = "validation.book.content.blank")
    private final String content;
    
    public SaveBookContentParameters(final Long bookId, final String content) {
        this.bookId = bookId;
        this.content = content;
        this.validateSelf();
    }
}
