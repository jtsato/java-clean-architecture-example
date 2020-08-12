package io.github.jtsato.bookstore.core.author.usecase.parameter;

import io.github.jtsato.bookstore.core.common.validation.LocalDateConstraint;
import io.github.jtsato.bookstore.core.common.validation.SelfValidating;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@ToString
public class SearchAuthorsParameters extends SelfValidating<SearchAuthorsParameters> {

    private Long id;

    private String name;

    private String gender;

    @LocalDateConstraint(message = "validation.author.start.birthdate.date.notvalid")
    private String startBirthdateDate;

    @LocalDateConstraint(message = "validation.author.end.birthdate.date.notvalid")
    private String endBirthdateDate;

    public SearchAuthorsParameters(final Long id, final String name, final String gender, final String startBirthdateDate, final String endBirthdateDate) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.startBirthdateDate = startBirthdateDate;
        this.endBirthdateDate = endBirthdateDate;
        this.validateSelf();
    }
}
