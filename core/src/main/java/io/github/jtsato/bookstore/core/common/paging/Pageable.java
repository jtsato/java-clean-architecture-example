package io.github.jtsato.bookstore.core.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
@ToString
public class Pageable {

    private Integer page;

    private Integer size;

    private Integer numberOfElements;

    private Long totalOfElements;

    private Integer totalPages;
}
