package io.github.jtsato.bookstore.core.enumerator.usecase.parameter;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class SearchEnumeratorsParameters {

    private Optional<String> domain;

    private Optional<String> key;

    public SearchEnumeratorsParameters(final Optional<String> domain, final Optional<String> key) {
        this.domain = domain;
        this.key = key;
    }
}
