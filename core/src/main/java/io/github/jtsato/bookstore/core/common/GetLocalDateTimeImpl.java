package io.github.jtsato.bookstore.core.common;

import java.time.LocalDateTime;

import jakarta.inject.Named;

/**
 * @author Jorge Takeshi Sato
 */

@Named
public class GetLocalDateTimeImpl implements GetLocalDateTime {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
