package io.github.jtsato.bookstore.core.author.usecase.impl;

import java.util.List;

import javax.inject.Named;

import org.apache.commons.collections.CollectionUtils;

import io.github.jtsato.bookstore.core.author.domain.Author;
import io.github.jtsato.bookstore.core.author.gateway.FindAuthorsByIdsGateway;
import io.github.jtsato.bookstore.core.author.usecase.FindAuthorsByIdsUseCase;
import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.exception.InvalidParameterException;
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
public class FindAuthorsByIdsUseCaseImpl implements FindAuthorsByIdsUseCase {

    private final FindAuthorsByIdsGateway findAuthorsByIdsGateway;

    @Override
    public Page<Author> findAuthorsByIds(final List<Long> ids) {

        if (CollectionUtils.isEmpty(ids)) {
            throw new InvalidParameterException("validation.authors.ids.null");
        }

        if (ids.size() > 1000) {
            throw new InvalidParameterException("validation.get.by.ids.limit");
        }

        return findAuthorsByIdsGateway.findAuthorsByIds(ids);
    }
}
