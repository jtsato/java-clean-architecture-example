package io.github.jtsato.bookstore.core.exception;

/**
 * @author Jorge Takeshi Sato
 */

public class InvalidEnumeratorException extends CoreException {

    private static final long serialVersionUID = 6498777380767738701L;

    public InvalidEnumeratorException(final String message, final Object... args) {
        super(message, args);
    }
}
