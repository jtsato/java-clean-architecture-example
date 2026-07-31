package io.github.jtsato.bookstore.core.enumerator.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.action.SearchEnumeratorsAction;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;

/**
 * @author Jorge Takeshi Sato
 */

@DisplayName("Search Enumerators")
class SearchEnumeratorsUseCaseTest {

    private final SearchEnumeratorsUseCase getBookByIdUseCase = new SearchEnumeratorsAction();

    @DisplayName("Successfully searches enumerators for each filter")
    @ParameterizedTest(name = "[{index}] domain={0}, key={1}")
    @MethodSource("filtersAndExpectedResultCounts")
    void successfullySearchesEnumeratorsForEachFilter(final String domain, final String key, final int expectedResultCount) {

        final SearchEnumeratorsParameters parameters = new SearchEnumeratorsParameters(domain, key);
        final List<Enumerator> enumerators = getBookByIdUseCase.execute(parameters);

        assertThat(enumerators).isNotNull().hasSize(expectedResultCount);
    }

    private static Stream<Arguments> filtersAndExpectedResultCounts() {
        return Stream.of(
                Arguments.of(null, null, 2),
                Arguments.of("Gender", null, 2),
                Arguments.of("Gender", "Female", 1),
                Arguments.of("X", null, 0),
                Arguments.of(null, "X", 0)
        );
    }
}
