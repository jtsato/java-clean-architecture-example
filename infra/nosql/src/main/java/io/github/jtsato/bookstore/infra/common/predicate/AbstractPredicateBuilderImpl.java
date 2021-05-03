package io.github.jtsato.bookstore.infra.common.predicate;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BeanPath;

/**
 * @author Jorge Takeshi Sato
 */

public abstract class AbstractPredicateBuilderImpl<P extends BeanPath<?>, Q> implements PredicateBuilder<Q> {

    protected final P beanPath;

    protected AbstractPredicateBuilderImpl(final P beanPath) {
        this.beanPath = beanPath;
    }

    @Override
    public BooleanBuilder buildBooleanBuilder(final Q query) {
        final BooleanBuilder booleanBuilder = new BooleanBuilder();
        buildBooleanExpressions(query).forEach(booleanBuilder::and);
        return booleanBuilder;
    }

    protected String addLikeOperator(final String value) {
        return "%" + value + "%";
    }
}
