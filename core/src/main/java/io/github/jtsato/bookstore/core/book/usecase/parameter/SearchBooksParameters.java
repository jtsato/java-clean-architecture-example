package io.github.jtsato.bookstore.core.book.usecase.parameter;

import io.github.jtsato.bookstore.core.author.usecase.parameter.SearchAuthorsParameters;
import io.github.jtsato.bookstore.core.common.validation.LocalDateTimeConstraint;
import io.github.jtsato.bookstore.core.common.validation.SelfValidating;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter 
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE) 
@EqualsAndHashCode(callSuper = false)
public class SearchBooksParameters extends SelfValidating<SearchBooksParameters> {
    
    private String title;
    
    private SearchAuthorsParameters authorParameters;
    
    @LocalDateTimeConstraint(message = "validation.book.start.creation.date.notvalid")
    private String startCreationDate;

    @LocalDateTimeConstraint(message = "validation.book.end.creation.date.notvalid")
    private String endCreationDate;
    
    public SearchBooksParameters(final String title, final SearchAuthorsParameters authorParameters, final String startCreationDate, final String endCreationDate) {
        this.title = title;
        this.authorParameters = authorParameters;
        this.startCreationDate = startCreationDate;
        this.endCreationDate = endCreationDate;
        this.validateSelf();
    }
}     