package io.github.jtsato.bookstore.dataprovider.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Jorge Takeshi Sato  
 */

class PageRequestHelperTest {

    @DisplayName("Successful to build page request if there is no parameters")
    @Test
    void successfulToBuildPageRequestIfThereIsNoParameters() {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(null, null, null);

        assertThat(pageRequest.getPageNumber()).isZero();
        assertThat(pageRequest.getOffset()).isZero();
        assertThat(pageRequest.getPageSize()).isEqualTo(10);

        assertThat(pageRequest.getSort()).isEqualTo(Sort.unsorted());
    }

    @DisplayName("Successful to build page request if there is parameters")
    @Test
    void successfulToBuildPageRequestIfThereIsParameters1() {

        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(1, 5, "field1:asc,field2:desc");

        assertThat(pageRequest.getPageNumber()).isOne();
        assertThat(pageRequest.getOffset()).isEqualTo(5);
        assertThat(pageRequest.getPageSize()).isEqualTo(5);

        assertThat(pageRequest.getSort()).isEqualTo(Sort.by(Sort.Direction.ASC, "field1").and(Sort.by(Sort.Direction.DESC, "field2")));
    }

    @DisplayName("Successful to build page request if there is parameters")
    @Test
    void successfulToBuildPageRequestIfThereIsParameters2() {
        final PageRequest pageRequest = PageRequestHelper.buildPageRequest(1, 0, "field1:asc,field2:desc");

        assertThat(pageRequest.getPageNumber()).isOne();
        assertThat(pageRequest.getOffset()).isEqualTo(10);
        assertThat(pageRequest.getPageSize()).isEqualTo(10);

        assertThat(pageRequest.getSort()).isEqualTo(Sort.by(Sort.Direction.ASC, "field1").and(Sort.by(Sort.Direction.DESC, "field2")));
    }

}
