package io.github.jtsato.bookstore.entrypoint.rest.enumerator.domain.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class EnumeratorResponse implements Serializable {

    private static final long serialVersionUID = -7218399148021136190L;

    private String domain;

    private String key;

    private String value;
}
