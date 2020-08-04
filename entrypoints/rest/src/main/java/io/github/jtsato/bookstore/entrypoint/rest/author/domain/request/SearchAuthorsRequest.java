package io.github.jtsato.bookstore.entrypoint.rest.author.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@AllArgsConstructor
public final class SearchAuthorsRequest implements Serializable {
    
    private static final long serialVersionUID = 6528126199921883953L;

    private final Long id;
    
    private final String name;
    
    private final String gender;
    
    private final String startBirthday;
    
    private final String endBirthday;
}
