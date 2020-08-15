package io.github.jtsato.bookstore.core.common;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumeratorUtils {

    public static <E extends Enum<E>> E valueOf(final String name, final Class<E> clazz) {
        final List<E> constants = Arrays.asList(clazz.getEnumConstants());
        final Predicate<? super E> byEquals = element -> element.toString().equals(name);
        return constants.stream().filter(byEquals).findFirst().orElseThrow(throwIllegalArgumentException(clazz.getSimpleName(), name, getValues(constants)));
    }

    private static <T extends Enum<T>> List<String> getValues(final List<T> values) {
        return values.stream().map(T::toString).collect(Collectors.toList());
    }

    private static Supplier<? extends InvalidParameterException> throwIllegalArgumentException(final String enumClass,
                                                                                               final String actual,
                                                                                               final List<String> values) {
        final String message = String.format("The value %s is invalid for %s. Valid values are: %s.", actual, enumClass, String.join(", ", values));
        return () -> new InvalidParameterException(message);
    }
}
