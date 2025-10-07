package api.common.annotation;

import api.common.validator.DateValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidDateTest {

    private DateValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new DateValidator();
        context = null;
    }

    @Test
    void shouldReturnTrueForValidIsoDate() {
        String validDate = "2025-10-03";

        boolean result = validator.isValid(validDate, context);

        assertTrue(result, "Expected true for valid ISO date");
    }

    @Test
    void shouldReturnFalseForInvalidDate() {
        String invalidDate = "2025-02-30T10:00:00";
        assertFalse(validator.isValid(invalidDate, null));
    }

    @Test
    void shouldReturnFalseForNullValue() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void shouldReturnFalseForEmptyString() {
        assertFalse(validator.isValid("", null));
    }
}

