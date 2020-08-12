package io.github.jtsato.bookstore.core.enumerator.usecase.parameter;

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

    private final String domain;

    private final String key;

    public SearchEnumeratorsParameters(final String domain, final String key) {
        this.domain = domain;
        this.key = key;
    }
}
