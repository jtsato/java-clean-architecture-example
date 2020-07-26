package io.github.jtsato.bookstore.dataprovider.database.common.predicate;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

/**
 * @author Jorge Takeshi Sato  
 */

public interface PredicateBuilder<Q> {

    List<BooleanExpression> buildBooleanExpressions(final Q query);

    BooleanBuilder buildBooleanBuilder(final Q query);
}
