package io.github.jtsato.bookstore.core.book.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class BookContent implements Serializable {

	private static final long serialVersionUID = 8678192311370127564L;

	private final Long id;

	private final Long bookId;

    private final String content;
    
    private final LocalDateTime creationDate;

    private final LocalDateTime updateDate;
}
