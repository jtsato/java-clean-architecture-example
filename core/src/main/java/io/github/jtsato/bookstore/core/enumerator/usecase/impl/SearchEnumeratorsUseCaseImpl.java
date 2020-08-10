package io.github.jtsato.bookstore.core.enumerator.usecase.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Named;

import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.enumerator.domain.Enumerator;
import io.github.jtsato.bookstore.core.enumerator.usecase.SearchEnumeratorsUseCase;
import io.github.jtsato.bookstore.core.enumerator.usecase.parameter.SearchEnumeratorsParameters;
import lombok.RequiredArgsConstructor;

/*
 * A Use Case follows these steps:
 * - Takes input
 * - Validates business rules
 * - Manipulates the model state
 * - Returns output
 */

/**
 * @author Jorge Takeshi Sato  
 */

@Named
@RequiredArgsConstructor
public class SearchEnumeratorsUseCaseImpl implements SearchEnumeratorsUseCase {

    @Override
    public List<Enumerator> execute(final SearchEnumeratorsParameters parameters) {
        final Predicate<? super Enumerator> filter = enumerator -> compare(enumerator, parameters.getDomain(), parameters.getKey());
        return getAllEnumerators().stream().filter(filter).collect(Collectors.toList());
    }

    private boolean compare(final Enumerator enumerator, final Optional<String> domain, final Optional<String> key) {
        final boolean domainNotProvidedOrSameDomain = domain.isEmpty() || enumerator.getDomain().equalsIgnoreCase(domain.get());
        final boolean keyNotProvidedOrSameKey = key.isEmpty() || enumerator.getKey().equalsIgnoreCase(key.get());
        return domainNotProvidedOrSameDomain && keyNotProvidedOrSameKey;
    }

    private List<Enumerator> getAllEnumerators() {
        final List<Enumerator> enumerators = new ArrayList<>();
        final List<Gender> genders = Arrays.asList(Gender.values());
        enumerators.addAll(genders.stream().map(this::buildEnumerator).collect(Collectors.toList()));
        return enumerators;
    }

    private Enumerator buildEnumerator(final Gender element) {
        return new Enumerator("Gender", element.name(), element.getMessageKey());
    }
}
