package io.github.jtsato.bookstore.dataprovider.book.repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.github.jtsato.bookstore.core.book.usecase.parameter.SearchBooksParameters;
import io.github.jtsato.bookstore.dataprovider.author.repository.AuthorPredicateBuilder;
import io.github.jtsato.bookstore.dataprovider.book.domain.QBookEntity;
import io.github.jtsato.bookstore.dataprovider.common.predicate.AbstractPredicateBuilderImpl;

/**
 * @author Jorge Takeshi Sato  
 */

public class BookPredicateBuilder extends AbstractPredicateBuilderImpl<QBookEntity, SearchBooksParameters> {

    public BookPredicateBuilder(final QBookEntity entityPath) {
        super(entityPath);
    }

    @Override
    public List<BooleanExpression> buildBooleanExpressions(final SearchBooksParameters query) {

        final List<BooleanExpression> booleanExpressions = new LinkedList<>();

        if (StringUtils.isNotBlank(query.getTitle())) {
            booleanExpressions.add(entityPath.title.like(addLikeOperator(query.getTitle())));
        }

        if (query.getAuthorParameters() != null) {
            final AuthorPredicateBuilder authorPredicateBuilder = new AuthorPredicateBuilder(entityPath.author);
            booleanExpressions.addAll(authorPredicateBuilder.buildBooleanExpressions(query.getAuthorParameters()));
        }

        if (StringUtils.isNotBlank(query.getStartCreationDate())) {
            final LocalDateTime startCreationDate = LocalDateTime.parse(query.getStartCreationDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.creationDate.goe(startCreationDate));
        }

        if (StringUtils.isNotBlank(query.getEndCreationDate())) {
            final LocalDateTime endCreationDate = LocalDateTime.parse(query.getEndCreationDate(), DateTimeFormatter.ISO_DATE_TIME);
            booleanExpressions.add(entityPath.creationDate.loe(endCreationDate));
        }

        return booleanExpressions;
    }
}
