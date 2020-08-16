package io.github.jtsato.bookstore.core.common.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jorge Takeshi Sato
 */

public class LocalDateValidator implements ConstraintValidator<LocalDateConstraint, String> {

    @Override
    public boolean isValid(final String candidate, final ConstraintValidatorContext constraintContext) {
        return candidate != null ? parseLocalDate(candidate) : Boolean.TRUE;
    }

    private boolean parseLocalDate(final String candidate) {
        try {
            LocalDate.parse(candidate, DateTimeFormatter.ISO_DATE);
        } catch (final Exception exception) {
            return false;
        }
        return true;
    }
}
