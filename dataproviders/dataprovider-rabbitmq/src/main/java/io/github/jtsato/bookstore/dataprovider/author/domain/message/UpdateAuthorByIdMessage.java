package io.github.jtsato.bookstore.dataprovider.author.domain.message;

import java.io.Serializable;

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
public class UpdateAuthorByIdMessage implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;

    private Long id;
    private String name;
    private String gender;
    private String birthdate;
}
