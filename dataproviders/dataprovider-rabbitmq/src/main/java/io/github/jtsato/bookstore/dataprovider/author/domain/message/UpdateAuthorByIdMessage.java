package io.github.jtsato.bookstore.dataprovider.author.domain.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@AllArgsConstructor
public class UpdateAuthorByIdMessage implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;

    private final Long id;

    private final String name;

    private final String gender;

    private final String birthdate;
}
