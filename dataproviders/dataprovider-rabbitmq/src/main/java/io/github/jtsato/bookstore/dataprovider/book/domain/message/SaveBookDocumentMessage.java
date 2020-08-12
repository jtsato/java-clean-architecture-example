package io.github.jtsato.bookstore.dataprovider.book.domain.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@AllArgsConstructor
public class SaveBookDocumentMessage implements Serializable {

    private static final long serialVersionUID = -1401793357376446399L;

    private final Long id;

    private final Long bookId;

    private final String contentType;

    private final String extension;

    private final String name;

    private final Long size;

    private final String content;
}
