package io.github.jtsato.bookstore.core.author.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Jorge Takeshi Sato  
 */

class GenderTest {

    @DisplayName("Sucessful to run gender functions")  
    @Test
    void sucessfulToRunGenderFunctions() {
        assertThat(Gender.MALE.is(Gender.MALE)).isTrue();
        assertThat(Gender.MALE.isNot(Gender.MALE)).isFalse();
        assertThat(Gender.MALE.isNot(Gender.FEMALE)).isTrue();
        assertThat(Gender.MALE.in(Gender.FEMALE)).isFalse();
        assertThat(Gender.MALE.notIn(Gender.MALE)).isFalse();
        assertThat(Gender.MALE.in(Gender.MALE)).isTrue();
        assertThat(Gender.MALE.notIn(Gender.FEMALE)).isTrue();
        assertThat(Gender.MALE.getMessageKey()).isEqualTo("enum-gender-male");
        assertThat(Gender.FEMALE.getMessageKey()).isEqualTo("enum-gender-female");
    }
}
