package api.common.annotation;

import api.common.validator.LocalTimeValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalTimeValidatorTest {

    private LocalTimeValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new LocalTimeValidator();
        context = null;
    }

    @Test
    void shouldReturnTrueForValidTime() {
        String validTime = "14:30:00";

        boolean result = validator.isValid(validTime, context);

        assertTrue(result, "Expected true for valid ISO local time (HH:mm:ss)");
    }

    @Test
    void shouldReturnFalseForInvalidTimeFormat() {
        String invalidTime = "14:30";

        boolean result = validator.isValid(invalidTime, context);

        assertFalse(result, "Expected false for invalid time format (missing seconds)");
    }

    @Test
    void shouldReturnFalseForNonexistentTime() {
        String invalidTime = "25:00:00";

        boolean result = validator.isValid(invalidTime, context);

        assertFalse(result, "Expected false for non-existent time (25:00:00)");
    }

    @Test
    void shouldReturnFalseForEmptyValue() {
        boolean result = validator.isValid("", context);

        assertFalse(result, "Expected false for empty value");
    }

    @Test
    void shouldReturnFalseForNullValue() {
        boolean result = validator.isValid(null, context);

        assertFalse(result, "Expected false for null value");
    }
}
