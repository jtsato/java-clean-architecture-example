package io.github.jtsato.bookstore.dataprovider.database.author.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.dataprovider.database.author.domain.QAuthorEntity;
import io.github.jtsato.bookstore.dataprovider.database.common.predicate.AbstractPredicateBuilderImpl;

/**
 * @author Jorge Takeshi Sato  
 */

public class AuthorPredicateBuilder extends AbstractPredicateBuilderImpl<QAuthorEntity, SearchAuthorsParameters> {

    public AuthorPredicateBuilder(final QAuthorEntity entityPath) {
        super(entityPath);
    }

    @Override
    public List<BooleanExpression> buildBooleanExpressions(final SearchAuthorsParameters query) {

        final List<BooleanExpression> booleanExpressions = new LinkedList<>();

        if (query.getId() != null) {
            booleanExpressions.add(entityPath.id.eq((query.getId())));
        }

        if (StringUtils.isNotBlank(query.getName())) {
            booleanExpressions.add(entityPath.name.like(addLikeOperator((query.getName()))));
        }

        if (StringUtils.isNotBlank(query.getGender())) {
            booleanExpressions.add(entityPath.gender.eq(query.getGender()));
        }

        if (StringUtils.isNotBlank(query.getStartBirthdayDate())) {
            final LocalDate startBirthdayDate = LocalDate.parse(query.getStartBirthdayDate(), DateTimeFormatter.ISO_DATE);
            booleanExpressions.add(entityPath.birthday.goe(startBirthdayDate));
        }

        if (StringUtils.isNotBlank(query.getEndBirthdayDate())) {
            final LocalDate endBirthdayDate = LocalDate.parse(query.getEndBirthdayDate(), DateTimeFormatter.ISO_DATE);
            booleanExpressions.add(entityPath.birthday.loe(endBirthdayDate));
        }

        return booleanExpressions;
    }
}
