package io.github.jtsato.bookstore.core.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Jorge Takeshi Sato  
 */

class GetLocalDateTimeTest {
    
    private GetLocalDateTime getLocalDateTime = new GetLocalDateTimeImpl();

    @DisplayName("Sucessful to run gender functions")  
    @Test
    void sucessfulToRunGenderFunctions() {
        assertThat(getLocalDateTime.now()).isNotNull();
        assertThat(getLocalDateTime.now()).isCloseTo(LocalDateTime.now(), within(1, ChronoUnit.SECONDS));
    }
}
