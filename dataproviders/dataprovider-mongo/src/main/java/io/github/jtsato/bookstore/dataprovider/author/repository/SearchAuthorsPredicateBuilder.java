package io.github.jtsato.bookstore.dataprovider.author.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.author.domain.Gender;
import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.EnumeratorUtils;
import io.github.jtsato.bookstore.dataprovider.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.dataprovider.common.predicate.AbstractPredicateBuilderImpl;

/**
 * @author Jorge Takeshi Satocd 
 */

public class SearchAuthorsPredicateBuilder extends AbstractPredicateBuilderImpl<QAuthorEntity, SearchAuthorsParameters> {

    public SearchAuthorsPredicateBuilder(final QAuthorEntity beanPath) {
        super(beanPath);
    }

    @Override
    public List<BooleanExpression> buildBooleanExpressions(final SearchAuthorsParameters query) {

        final List<BooleanExpression> booleanExpressions = new LinkedList<>();

        if (query.getId() != null) {
            booleanExpressions.add(beanPath.authorId.eq(query.getId()));
        }

        if (StringUtils.isNotBlank(query.getName())) {
            booleanExpressions.add(beanPath.name.like(addLikeOperator(query.getName())));
        }

        if (StringUtils.isNotBlank(query.getGender())) {
            final Gender gender = EnumeratorUtils.valueOf(query.getGender(), Gender.class);
            booleanExpressions.add(beanPath.gender.eq(gender.name()));
        }

        if (StringUtils.isNotBlank(query.getStartBirthdateDate())) {
            final LocalDate startBirthdateDate = LocalDate.parse(query.getStartBirthdateDate(), DateTimeFormatter.ISO_DATE);
            booleanExpressions.add(beanPath.birthdate.goe(startBirthdateDate));
        }

        if (StringUtils.isNotBlank(query.getEndBirthdateDate())) {
            final LocalDate endBirthdateDate = LocalDate.parse(query.getEndBirthdateDate(), DateTimeFormatter.ISO_DATE);
            booleanExpressions.add(beanPath.birthdate.loe(endBirthdateDate));
        }

        return booleanExpressions;
    }
}
