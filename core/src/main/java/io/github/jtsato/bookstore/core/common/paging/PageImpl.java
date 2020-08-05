package io.github.jtsato.bookstore.core.common.paging;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class PageImpl<T> implements Page<T> {

    private final List<T> content;

    private final Pageable pageable;
}
