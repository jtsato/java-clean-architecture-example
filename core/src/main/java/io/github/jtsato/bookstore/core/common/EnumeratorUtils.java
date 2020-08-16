package io.github.jtsato.bookstore.core.common;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import io.github.jtsato.bookstore.core.exception.InvalidEnumeratorException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnumeratorUtils {

    public static <E extends Enum<E>> E valueOf(final String candidate, final Class<E> clazz) {
        final List<E> values = Arrays.asList(clazz.getEnumConstants());
        final Predicate<? super E> byEquals = element -> element.name().equals(candidate);
        final List<String> valuesAsString = getValues(values);
        return values.stream().filter(byEquals).findFirst().orElseThrow(throwIllegalArgumentException(clazz.getSimpleName(), candidate, valuesAsString));
    }

    private static <T extends Enum<T>> List<String> getValues(final List<T> values) {
        return values.stream().map(T::toString).collect(Collectors.toList());
    }

    private static Supplier<? extends InvalidEnumeratorException> throwIllegalArgumentException(final String enumClass,
                                                                                                final String actual,
                                                                                                final List<String> values) {
        return () -> new InvalidEnumeratorException("validation.enumerator.value.invalid", actual, enumClass, String.join(", ", values));
    }
}
