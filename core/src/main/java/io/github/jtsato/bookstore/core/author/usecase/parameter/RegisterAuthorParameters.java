package io.github.jtsato.bookstore.core.author.usecase.parameter;

import javax.validation.constraints.NotBlank;

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
public class RegisterAuthorParameters extends SelfValidating<RegisterAuthorParameters> {

    @NotBlank(message = "validation.author.name.blank")
    private final String name;

    @NotBlank(message = "validation.author.gender.blank")
    private final String gender;

    @NotBlank(message = "validation.author.birthdate.blank")
    @LocalDateConstraint(message = "validation.author.birthdate.notvalid")
    private final String birthdate;

    public RegisterAuthorParameters(final String name, final String gender, final String birthdate) {
        this.name = name;
        this.gender = gender;
        this.birthdate = birthdate;
        this.validateSelf();
    }
}
