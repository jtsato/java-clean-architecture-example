package io.github.jtsato.bookstore.core.author.usecase.parameter;

import javax.validation.constraints.NotBlank;

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
public class RegisterAuthorParameters extends SelfValidating<RegisterAuthorParameters> {

    @NotBlank(message = "validation.author.name.blank")
    private final String name;

    @NotBlank(message = "validation.author.gender.blank")
    private final String gender;

    @NotBlank(message = "validation.author.birthday.blank")
    @LocalDateConstraint(message = "validation.author.birthday.notvalid")
    private final String birthday;

    public RegisterAuthorParameters(final String name, final String gender, final String birthday) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.validateSelf();
    }
}
