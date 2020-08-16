package io.github.jtsato.bookstore.entrypoint.rest.author.domain.request;

import java.io.Serializable;
import java.util.List;

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
public final class FindAuthorsByIdsRequest implements Serializable {

    private static final long serialVersionUID = 7832871097344659884L;

    private List<Long> ids;
}
