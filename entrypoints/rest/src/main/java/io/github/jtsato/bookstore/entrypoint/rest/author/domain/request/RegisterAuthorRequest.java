package io.github.jtsato.bookstore.entrypoint.rest.author.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAuthorRequest implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;

    private String name;

    private String gender;
    
    private String birthday;
}
