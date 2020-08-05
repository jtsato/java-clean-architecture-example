package io.github.jtsato.bookstore.dataprovider.author.domain.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class RegisterAuthorMessage implements Serializable {

    private static final long serialVersionUID = 1691977636209328459L;

    private final String name;

    private final String gender;

    private final String birthday;
}
