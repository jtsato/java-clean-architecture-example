package io.github.jtsato.bookstore.core.enumerator.usecase;

import java.util.List;

import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;

/**
 * @author Jorge Takeshi Sato
 */

@FunctionalInterface
public interface SearchEnumeratorsUseCase {

    List<Enumerator> execute(final SearchEnumeratorsParameters searchEnumeratorsParameters);
}
