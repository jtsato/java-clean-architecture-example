package io.github.jtsato.bookstore.core.book.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"content"})
public class BookDocument implements Serializable {

    private static final long serialVersionUID = 8678192311370127564L;

    private final Long id;

    private final Long bookId;

    @Setter
    private String contentType;

    @Setter
    private String extension;

    @Setter
    private String name;

    @Setter
    private Long size;

    @Setter
    private String content;

    private final LocalDateTime creationDate;

    @Setter
    private LocalDateTime updateDate;
}
