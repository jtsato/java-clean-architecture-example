package io.github.jtsato.bookstore.infra.book.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.infra.author.repository.SearchAuthorsPredicateBuilder;
import io.github.jtsato.bookstore.infra.book.domain.QBookEntity;
import io.github.jtsato.bookstore.infra.common.predicate.AbstractPredicateBuilderImpl;

/**
 * @author Jorge Takeshi Sato
 */

public class SearchBooksPredicateBuilder extends AbstractPredicateBuilderImpl<QBookEntity, SearchBooksParameters> {

    public SearchBooksPredicateBuilder(final QBookEntity entityPath) {
        super(entityPath);
    }

    @Override
    public List<BooleanExpression> buildBooleanExpressions(final SearchBooksParameters query) {

        final List<BooleanExpression> booleanExpressions = new LinkedList<>();

        if (query.getSearchAuthorsParameters() != null) {
            final SearchAuthorsPredicateBuilder searchAuthorsPredicateBuilder = new SearchAuthorsPredicateBuilder(entityPath.author);
            booleanExpressions.addAll(searchAuthorsPredicateBuilder.buildBooleanExpressions(query.getSearchAuthorsParameters()));
        }

        if (StringUtils.isNotBlank(query.getTitle())) {
            booleanExpressions.add(entityPath.title.likeIgnoreCase(addLikeOperator(query.getTitle())));
        }

        if (query.getStartPrice() != null) {
            booleanExpressions.add(entityPath.price.goe(query.getStartPrice()));
        }

        if (query.getEndPrice() != null) {
            booleanExpressions.add(entityPath.price.loe(query.getEndPrice()));
        }

        if (query.getAvailable() != null) {
            booleanExpressions.add(entityPath.available.eq(query.getAvailable()));
        }

        if (StringUtils.isNotBlank(query.getStartCreationDate())) {
            final LocalDateTime startCreationDate = LocalDateTime.parse(query.getStartCreationDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.creationDate.goe(startCreationDate));
        }

        if (StringUtils.isNotBlank(query.getEndCreationDate())) {
            final LocalDateTime endCreationDate = LocalDateTime.parse(query.getEndCreationDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.creationDate.loe(endCreationDate));
        }

        if (StringUtils.isNotBlank(query.getStartUpdateDate())) {
            final LocalDateTime startUpdateDate = LocalDateTime.parse(query.getStartUpdateDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.updateDate.goe(startUpdateDate));
        }

        if (StringUtils.isNotBlank(query.getEndUpdateDate())) {
            final LocalDateTime endUpdateDate = LocalDateTime.parse(query.getEndUpdateDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.updateDate.loe(endUpdateDate));
        }

        return booleanExpressions;
    }
}
