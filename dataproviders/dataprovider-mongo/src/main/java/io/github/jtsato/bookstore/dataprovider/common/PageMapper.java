package io.github.jtsato.bookstore.dataprovider.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import io.github.jtsato.bookstore.core.common.paging.Page;
import io.github.jtsato.bookstore.core.common.paging.PageImpl;
import io.github.jtsato.bookstore.core.common.paging.Pageable;

/**
 * @author Jorge Takeshi Sato
 */

public interface PageMapper<T, K> {

    default Page<T> of(final org.springframework.data.domain.Page<K> page, final Function<K, T> elementMapper) {
        final Pageable pageable = of(page);
        final List<T> content = of(page.getContent(), elementMapper);
        return new PageImpl<>(content, pageable);
    }

    default Pageable of(final org.springframework.data.domain.Page<K> page) {

        final org.springframework.data.domain.Pageable pageable = page.getPageable();

        final Integer pageNumber = pageable.getPageNumber();
        final Integer size = pageable.getPageSize();
        final Integer numberOfElements = page.getNumberOfElements();
        final Integer totalOfPages = page.getTotalPages();
        final Long totalOfElements = page.getTotalElements();

        return new Pageable(pageNumber, size, numberOfElements, totalOfElements, totalOfPages);
    }

    default List<T> of(final List<K> elements, final Function<K, T> mapper) {
        final List<T> result = new ArrayList<>(elements.size());
        elements.forEach(element -> result.add(mapper.apply(element)));
        return result;
    }
}
