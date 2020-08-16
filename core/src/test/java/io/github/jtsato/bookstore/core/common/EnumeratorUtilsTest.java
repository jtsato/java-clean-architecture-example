package io.github.jtsato.bookstore.core.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.jtsato.bookstore.core.exception.InvalidEnumeratorException;

/**
 * @author Jorge Takeshi Sato
 */

class EnumeratorUtilsTest {

    enum Semaphore {
            GREEN, YELLOW, RED
    }

    @DisplayName("Success to get value if found")
    @Test
    void successToGetValueIfFound() {

        final Semaphore green = EnumeratorUtils.valueOf("GREEN", Semaphore.class);
        assertThat(green).isEqualTo(Semaphore.GREEN);

        final Semaphore yellow = EnumeratorUtils.valueOf("YELLOW", Semaphore.class);
        assertThat(yellow).isEqualTo(Semaphore.YELLOW);

        final Semaphore red = EnumeratorUtils.valueOf("RED", Semaphore.class);
        assertThat(red).isEqualTo(Semaphore.RED);
    }

    @DisplayName("Fail to get value if not found")
    @Test
    void failToGetValueIfNotFound() {

        final Exception exception = Assertions.assertThrows(Exception.class, () -> EnumeratorUtils.valueOf("BLUE", Semaphore.class));

        assertThat(exception).isInstanceOf(InvalidEnumeratorException.class);
        
        final InvalidEnumeratorException invalidEnumeratorException = (InvalidEnumeratorException) exception; 

        assertThat(invalidEnumeratorException.getMessage()).isNotNull();
        assertThat(invalidEnumeratorException.getMessage()).isEqualTo("validation.enumerator.value.invalid");
        
        assertThat(invalidEnumeratorException.getArgs()).isNotNull().isNotEmpty().hasSize(3);
        assertThat(invalidEnumeratorException.getArgs()[0]).isEqualTo("BLUE");
        assertThat(invalidEnumeratorException.getArgs()[1]).isEqualTo("Semaphore");
        assertThat(invalidEnumeratorException.getArgs()[2]).isEqualTo("GREEN, YELLOW, RED");
    }
}
