package io.github.jtsato.bookstore.infra.author.domain.message;

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
public class RegisterAuthorMessage implements Serializable {

    private static final long serialVersionUID = 1691977636209328459L;

    private String name;
    private String gender;
    private String birthdate;
}
