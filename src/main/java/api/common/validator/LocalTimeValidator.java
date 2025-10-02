package api.common.validator;

import api.common.annotation.ValidLocalTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeValidator implements ConstraintValidator<ValidLocalTime, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }

        try {
            LocalTime.parse(value, DateTimeFormatter.ISO_LOCAL_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
