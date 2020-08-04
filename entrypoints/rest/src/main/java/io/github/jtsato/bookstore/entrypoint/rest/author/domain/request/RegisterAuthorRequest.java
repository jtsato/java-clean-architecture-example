package io.github.jtsato.bookstore.entrypoint.rest.author.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class RegisterAuthorRequest implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;

    private final String name;

    private final String gender;
    
    private final String birthday;
}
