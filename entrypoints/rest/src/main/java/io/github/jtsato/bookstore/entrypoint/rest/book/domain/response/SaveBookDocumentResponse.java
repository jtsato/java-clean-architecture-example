package io.github.jtsato.bookstore.entrypoint.rest.book.domain.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@AllArgsConstructor
public class SaveBookDocumentResponse implements Serializable {

    private static final long serialVersionUID = 3920772653334658040L;

    private final Long id;
    private final Long bookId;
    private final String contentType;
    private final String extension;
    private final String name;
    private final Long size;
    private final LocalDateTime creationDate;
    private final LocalDateTime updateDate;
}
