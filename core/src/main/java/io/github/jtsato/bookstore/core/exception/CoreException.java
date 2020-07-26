package io.github.jtsato.bookstore.core.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@AllArgsConstructor
public class CoreException extends RuntimeException {

    private static final long serialVersionUID = 7364813037155443755L;

    @Getter
    private final String message;

    @Getter
    private final transient Object[] args;
    
    public CoreException(final String message) {
        super();
        this.message = message;
        this.args = new Object[] {};
    }
}
