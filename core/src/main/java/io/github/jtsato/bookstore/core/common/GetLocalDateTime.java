package io.github.jtsato.bookstore.core.common;

import java.time.LocalDateTime;

/**
 * @author Jorge Takeshi Sato  
 */

@FunctionalInterface
public interface GetLocalDateTime {

    LocalDateTime now();
}
