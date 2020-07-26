package io.github.jtsato.bookstore.core.author.usecase.parameter;

import io.github.jtsato.bookstore.core.common.validation.LocalDateConstraint;
import io.github.jtsato.bookstore.core.common.validation.SelfValidating;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class SearchAuthorsParameters extends SelfValidating<SearchAuthorsParameters> {

    private Long id;
    
    private String name;

    private String gender;

    @LocalDateConstraint(message = "validation.author.start.birthday.date.notvalid")
    private String startBirthdayDate;

    @LocalDateConstraint(message = "validation.author.end.birthday.date.notvalid")
    private String endBirthdayDate;

    public SearchAuthorsParameters(final Long id, final String name, final String gender, final String startBirthdayDate, final String endBirthdayDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.startBirthdayDate = startBirthdayDate;
        this.endBirthdayDate = endBirthdayDate;
        this.validateSelf();
    }
}
