package io.github.jtsato.bookstore.entrypoint.rest.author.domain.response;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UpdateAuthorByIdResponse implements Serializable {

    private static final long serialVersionUID = -8133045476467957735L;

    private final Long id;

    private final String name;

    private final String gender;

    private final LocalDate birthday;
}
