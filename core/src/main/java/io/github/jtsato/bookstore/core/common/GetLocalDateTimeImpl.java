package io.github.jtsato.bookstore.core.common;

import java.time.LocalDateTime;

/**
 * @author Jorge Takeshi Sato  
 */

public class GetLocalDateTimeImpl implements GetLocalDateTime {

    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
