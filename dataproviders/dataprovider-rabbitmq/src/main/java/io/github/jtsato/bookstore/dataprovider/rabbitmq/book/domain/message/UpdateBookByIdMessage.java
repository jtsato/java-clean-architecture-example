package io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class UpdateBookByIdMessage implements Serializable {

	private static final long serialVersionUID = -3128994081332681868L;

	private final Long id;

	private final Long authorId;

	private final String title;

	private final BigDecimal price;

	private final Boolean available;
}
