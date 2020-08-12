package io.github.jtsato.bookstore.core.author.domain;


import java.util.Arrays;

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

    public abstract String getMessageKey();

    public boolean is(final Gender other) {
        return equals(other);
    }

    public boolean isNot(final Gender other) {
        return !is(other);
    }

    public boolean in(final Gender... others) {
        return Arrays.asList(others).contains(this);
    }

    public boolean notIn(final Gender... others) {
        return Arrays.stream(others).noneMatch(this::equals);
    }
}
