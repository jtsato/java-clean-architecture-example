package io.github.jtsato.bookstore.dataprovider.book.domain.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SaveBookDocumentMessage implements Serializable {

    private static final long serialVersionUID = -1401793357376446399L;

    private Long id;
    private Long bookId;
    private String contentType;
    private String extension;
    private String name;
    private Long size;
    @JsonIgnore
    private String content;
}
