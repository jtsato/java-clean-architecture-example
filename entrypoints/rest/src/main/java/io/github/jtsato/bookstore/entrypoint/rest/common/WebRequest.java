package io.github.jtsato.bookstore.entrypoint.rest.common;

import java.io.Serializable;

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
public class WebRequest implements Serializable {

    private static final long serialVersionUID = 8255224016531189463L;
    
    protected final String path;
}