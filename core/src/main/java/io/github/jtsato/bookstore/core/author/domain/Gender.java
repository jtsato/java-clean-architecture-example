package io.github.jtsato.bookstore.core.author.domain;


import java.util.Arrays;

import lombok.Generated;

/**
 * @author Jorge Takeshi Sato  
 */

public enum Gender {

        MALE {

            @Override
            public String getMessageKey() {
                return "enum-gender-male";
            }
        },

        FEMALE {

            @Override
            public String getMessageKey() {
                return "enum-gender-female";
            }
        };

    @Generated
    public String getMessageKey() {
        return null;
    }

    public boolean is(final Gender other) {
        return equals(other);
    }

    public boolean isNot(final Gender other) {
        return !is(other);
    }

    public boolean in(final Gender... others) {
        return Arrays.asList(others).stream().anyMatch(this::equals);
    }

    public boolean notIn(final Gender... others) {
        return Arrays.asList(others).stream().noneMatch(this::equals);
    }
}