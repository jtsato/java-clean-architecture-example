package io.github.jtsato.bookstore.core.book.usecase.parameter;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class SaveBookDocumentParameters extends SelfValidating<SaveBookDocumentParameters> implements Serializable {

    private static final long serialVersionUID = 6255672516286855379L;

    @NotNull(message = "validation.book.id.null")
    private final Long bookId;

    private final String contentType;

    private final String extension;

    @NotNull(message = "validation.book.document.name.blank")
    private final String name;

    @NotNull(message = "validation.book.document.size.empty")
    private final Long size;

    @NotBlank(message = "validation.book.content.blank")
    private final String content;

    public SaveBookDocumentParameters(final Long bookId,
                                      final String contentType,
                                      final String extension,
                                      final String name,
                                      final Long size,
                                      final String content) {
        this.bookId = bookId;
        this.contentType = contentType;
        this.extension = extension;
        this.name = name;
        this.size = size;
        this.content = content;
        this.validateSelf();
    }
}
