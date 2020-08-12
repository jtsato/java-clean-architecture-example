package io.github.jtsato.bookstore.entrypoint.rest.author.domain.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class SearchAuthorsRequest implements Serializable {

    private static final long serialVersionUID = 6528126199921883953L;

    private Long id;

    private String name;

    private String gender;

    private String startBirthdate;

    private String endBirthdate;
}
