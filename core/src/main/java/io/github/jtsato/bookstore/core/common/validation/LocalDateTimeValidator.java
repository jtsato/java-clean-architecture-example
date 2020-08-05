package io.github.jtsato.bookstore.core.common.validation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Jorge Takeshi Sato  
 */

public class LocalDateTimeValidator implements ConstraintValidator<LocalDateTimeConstraint, String> {

    @Override
    public boolean isValid(final String candidate, final ConstraintValidatorContext constraintContext) {

        if (candidate != null) {

            try {
                LocalDateTime.parse(candidate, DateTimeFormatter.ISO_DATE_TIME);
            } catch (final Exception exception) {
                return false;
            }
        }

        return true;
    }
}
