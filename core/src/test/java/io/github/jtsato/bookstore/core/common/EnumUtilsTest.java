package io.github.jtsato.bookstore.core.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.jtsato.bookstore.core.exception.InvalidParameterException;

/**
 * @author Jorge Takeshi Sato  
 */

class EnumUtilsTest {

    enum Semaphore {
            GREEN, YELLOW, RED
    };

    @DisplayName("Fail to get value if not found")
    @Test
    void failToGetValueIfNotFound() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> {
            EnumUtils.valueOf("BLUE", Semaphore.class);
        });

        assertThat(exception).isInstanceOf(InvalidParameterException.class);

        assertThat(exception.getMessage()).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("The value BLUE is not valid for Semaphore. Valid values are: GREEN, YELLOW, RED.");
    }
}
